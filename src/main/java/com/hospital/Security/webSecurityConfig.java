package com.hospital.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class webSecurityConfig {

    private  final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return  httpSecurity
                .csrf(csrf -> csrf.disable())   // disable CSRF for REST API
                .sessionManagement(
                        sessionConfig ->
                                sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/auth/**").permitAll()
                                .anyRequest().authenticated()
//                        .requestMatchers("/admin/**").authenticated()
//                        .requestMatchers("/doctor/**").authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin(Customizer.withDefaults()) // we don't need there form since ui will have it
                .httpBasic(Customizer.withDefaults()) // since you're testing via Postman with basic auth
                .build();
    }
}
