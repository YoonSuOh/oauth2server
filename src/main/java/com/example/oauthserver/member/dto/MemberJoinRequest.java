package com.example.oauthserver.member.dto;

import com.example.oauthserver.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
public class MemberJoinRequest {
    private String id;
    private String password;
    private String name;
    private String nickname;
    private String email;
}
