package com.hospital.Controller;


import com.hospital.Service.AuthService;
import com.hospital.dto.LoginRequestDto;
import com.hospital.dto.LoginresponseDto;
import com.hospital.dto.SignUpRequestDto;
import com.hospital.dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginresponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
            return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/SignUp")
    public ResponseEntity<SignUpResponseDto> login(@RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.ok(authService.signUp(signUpRequestDto));
    }
}
