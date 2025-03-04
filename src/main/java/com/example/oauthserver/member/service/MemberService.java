package com.example.oauthserver.member.service;

import com.example.oauthserver.member.dto.MemberJoinRequest;
import com.example.oauthserver.member.dto.MemberJoinResponse;
import com.example.oauthserver.member.entity.Member;
import com.example.oauthserver.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Service
@AllArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    /* 회원가입 */
    public MemberJoinResponse join(MemberJoinRequest memberJoinRequest){
        MemberJoinResponse memberJoinResponse = new MemberJoinResponse();
        if(memberRepository.findMemberById(memberJoinRequest.getId()).isPresent()){
            memberJoinResponse.setStatusCode(0);
            memberJoinResponse.setMessage("이미 가입된 회원입니다.");
        } else {
            Member member = Member.builder()
                    .id(memberJoinRequest.getId())
                    .password(memberJoinRequest.getPassword())
                    .name(memberJoinRequest.getName())
                    .nickname(memberJoinRequest.getNickname())
                    .email(memberJoinRequest.getEmail())
                    .createdAt(LocalDateTime.now())
                    .updateAt(LocalDateTime.now())
                    .build();
            memberRepository.save(member);
            memberJoinResponse.setStatusCode(1);
            memberJoinResponse.setId(member.getId());
            memberJoinResponse.setMessage("회원가입 성공");
        }
        return memberJoinResponse;
    }
}
