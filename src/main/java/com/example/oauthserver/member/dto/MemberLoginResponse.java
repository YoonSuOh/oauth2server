package com.example.oauthserver.member.dto;

import lombok.Data;

@Data
public class MemberLoginResponse {
    int statusCode;
    String id;
    String message;
}
