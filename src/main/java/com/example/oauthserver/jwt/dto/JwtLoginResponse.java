package com.example.oauthserver.jwt.dto;

import lombok.Data;

@Data
public class JwtLoginResponse {
    String accessToken;
    String refreshToken;
}
