package com.example.oauthserver.jwt.controller;

import com.example.oauthserver.jwt.dto.JwtLoginRequest;
import com.example.oauthserver.jwt.dto.JwtLoginResponse;
import com.example.oauthserver.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtLoginRequest request) {
        JwtLoginResponse jwtLoginResponse = authService.jwtLoginResponse(request);
        String accessToken = jwtLoginResponse.getAccessToken();
        String refreshToken = jwtLoginResponse.getRefreshToken();

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }
}
