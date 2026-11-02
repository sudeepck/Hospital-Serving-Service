package com.hospital.Service;

import com.hospital.Entity.Type.AuthProviderType;
import com.hospital.Entity.User;
import com.hospital.Repository.UserRepository;
import com.hospital.Security.AuthUtil;
import com.hospital.dto.LoginRequestDto;
import com.hospital.dto.LoginresponseDto;
import com.hospital.dto.SignUpRequestDto;
import com.hospital.dto.SignUpResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private  final AuthUtil authUtil;
    private  final  PasswordEncoder passwordEncoder;

    public LoginresponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),  loginRequestDto.getPassword())
            );

        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);
        return new LoginresponseDto(token,user.getId());
    }

    public User signUpInternal(SignUpRequestDto signUpRequestDto, AuthProviderType authProviderType, String providerId) {

        userRepository.findByUsername(signUpRequestDto.getUsername())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("User already exists: "
                            + signUpRequestDto.getUsername());
                });

        User user =  userRepository.save(User.builder()
                .username(signUpRequestDto.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .build()
        );

        if(authProviderType == AuthProviderType.EMAIL)
            user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        return  userRepository.save(user);
    }

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        User user = signUpInternal(signUpRequestDto,AuthProviderType.EMAIL,null);
        return new SignUpResponseDto(user.getId(), user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginresponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {

        //fetch provider Id(dfckbk) &  providerType ( google,facebook)
        AuthProviderType providerType =  authUtil.getProviderTypeFromRegistrationId(registrationId); // this will be provider
        String providerId = authUtil.getProviderIdFromOAuth2User(oAuth2User,registrationId); // this will be ID

        // save the providerType & providerId info with user ( so user should not be allowed to login via normal email again)
        User user = userRepository.findByProviderIdAndProviderType(providerId,providerType).orElse(null);
        String email = oAuth2User.getAttribute("email");
        User emailUser = userRepository.findByUsername(email).orElse(null);
        //First login ever. Neither the provider ID nor the email exists, so you create the account.
        if(user == null && emailUser == null){
            //signUpFlow
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User,registrationId,providerId);
            user = signUpInternal(new SignUpRequestDto(username, null), providerType,providerId);
        }else if(user != null){
            //The user has logged in with this provider before. The inner if runs only when the email from the provide0
            // r differs from the stored username, for example if they changed their Google email.
            // Then you update the username. 4
            // If the email is the same, nothing changes and you go straight to issuing the token.
            if(email != null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else{
            //Someone is logging in through Google for the first time, but an account with that email already exists.
            // That is usually a person who signed up with a password.
            // You throw, so a Google login can’t take over a password account. This is the intended behaviour.
            throw  new BadCredentialsException("This email is already registared with provider" + email);
        }
        LoginresponseDto loginresponseDto = new LoginresponseDto(authUtil.generateAccessToken(user), user.getId());
        return ResponseEntity.ok(loginresponseDto);
    }
}
