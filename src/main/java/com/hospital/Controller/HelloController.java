package com.hospital.Controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HelloController {

    @GetMapping("/")
    public String hello(HttpServletRequest request){
        return "HEllo World";
    }


    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard(HttpServletRequest request) {
        // You can fetch session values set in OAuth2SuccessHandler if needed
        String jwt = (String) request.getSession().getAttribute("jwt");
        Long userId = (Long) request.getSession().getAttribute("userId");

        return ResponseEntity.ok("Welcome to Dashboard! JWT: " + jwt + ", User ID: " + userId);
    }
}
