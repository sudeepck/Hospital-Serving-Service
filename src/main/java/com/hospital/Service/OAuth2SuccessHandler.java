package com.hospital.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.dto.LoginresponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;  //token :  OAuth2AuthenticationToken
        OAuth2User oAuth2User = (OAuth2User)  authentication.getPrincipal();// oAuth2User : name, aud, email etc
        String registrationId = token.getAuthorizedClientRegistrationId(); // registrationId :  google

        ResponseEntity<LoginresponseDto> loginResponse = authService.handleOAuth2LoginRequest(oAuth2User,registrationId);

        response.setStatus(loginResponse.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse.getBody()));
//        System.out.println("token : " + " " +  token);
//        System.out.println("oAuth2User : " + " " +  oAuth2User);
//        System.out.println("registrationId : " + " " +  registrationId);
    }
}
