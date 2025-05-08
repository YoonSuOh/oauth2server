package com.example.oauthserver.jwt.dto;

import lombok.Data;

@Data
public class JwtLoginRequest {
    String id;
    String password;
}
