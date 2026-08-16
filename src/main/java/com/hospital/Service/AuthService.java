package com.hospital.Service;

import com.hospital.Entity.User;
import com.hospital.Repository.UserRepository;
import com.hospital.Security.AuthUtil;
import com.hospital.dto.LoginRequestDto;
import com.hospital.dto.LoginresponseDto;
import com.hospital.dto.SignUpRequestDto;
import com.hospital.dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        userRepository.findByUsername(signUpRequestDto.getUsername())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("User already exists: "
                            + signUpRequestDto.getUsername());
                });

        User user = userRepository.save(User.builder()
                .username(signUpRequestDto.getUsername())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .build()
        );

        return new SignUpResponseDto(user.getId(), user.getUsername());
    }
}
