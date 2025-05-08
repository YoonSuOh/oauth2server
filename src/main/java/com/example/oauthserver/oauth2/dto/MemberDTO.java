package com.example.oauthserver.oauth2.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private String userId;
    private String nickname;
    private String role;
    private String phoneNumber;
    private String address;
}