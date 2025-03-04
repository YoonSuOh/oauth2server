package com.example.oauthserver.member.dto;

import lombok.Data;

@Data
public class MemberJoinResponse {
    int statusCode;
    String id;
    String message;
}
