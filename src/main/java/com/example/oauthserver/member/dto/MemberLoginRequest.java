package com.example.oauthserver.member.dto;

import lombok.Data;

@Data
public class MemberLoginRequest {
    String id;
    String password;
}
