package com.example.book_service.controller;

import com.example.book_service.JWT.JWTUtil;
import com.example.book_service.dto.AuthenticationResponse;
import com.example.book_service.dto.PasswordRequest;
import com.example.book_service.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/verify-password")
    public ResponseEntity<?> verifyPassword(@RequestHeader("Authorization") String token, @RequestBody PasswordRequest passwordRequest) {
        // JWT 토큰에서 "Bearer " 접두어를 제거
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = jwtUtil.getUsername(cleanedToken);
        String rawPassword = passwordRequest.getPassword();

        if (rawPassword == null) {
            System.out.println("Received null password for user " + username);
            return ResponseEntity.badRequest().body("Password cannot be null or empty");
        }


        if (authenticationService.validatePassword(username, rawPassword)) {
            return ResponseEntity.ok().body(new AuthenticationResponse("Password is valid", username));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse("Invalid password", username));
        }
    }
}
