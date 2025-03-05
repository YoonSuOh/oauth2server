package com.example.oauthserver.member.service;

import com.example.oauthserver.member.dto.MemberJoinRequest;
import com.example.oauthserver.member.dto.MemberJoinResponse;
import com.example.oauthserver.member.dto.MemberLoginRequest;
import com.example.oauthserver.member.dto.MemberLoginResponse;
import com.example.oauthserver.member.entity.Member;
import com.example.oauthserver.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /* 회원가입 */
    public MemberJoinResponse join(MemberJoinRequest memberJoinRequest){
        MemberJoinResponse memberJoinResponse = new MemberJoinResponse();
        if(memberRepository.findMemberById(memberJoinRequest.getId()).isPresent()){
            memberJoinResponse.setStatusCode(401);
            memberJoinResponse.setMessage("이미 가입된 회원입니다.");
        } else {
            Member member = Member.builder()
                    .id(memberJoinRequest.getId())
                    .password(bCryptPasswordEncoder.encode(memberJoinRequest.getPassword()))
                    .name(memberJoinRequest.getName())
                    .nickname(memberJoinRequest.getNickname())
                    .email(memberJoinRequest.getEmail())
                    .createdAt(LocalDateTime.now())
                    .updateAt(LocalDateTime.now())
                    .build();
            memberRepository.save(member);
            memberJoinResponse.setStatusCode(200);
            memberJoinResponse.setId(member.getId());
            memberJoinResponse.setMessage("회원가입 성공");
        }
        return memberJoinResponse;
    }

    /* 로그인 */
    public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
        MemberLoginResponse memberLoginResponse = new MemberLoginResponse();
        Optional<Member> optionalMember = memberRepository.findMemberById(memberLoginRequest.getId());
        if(optionalMember.isEmpty()){
            memberLoginResponse.setStatusCode(401);
            memberLoginResponse.setMessage("회원 정보를 찾을 수 없습니다.");
            return memberLoginResponse;
        }

        Member member = optionalMember.get();

        if(!bCryptPasswordEncoder.matches(memberLoginRequest.getPassword(), member.getPassword())){
            memberLoginResponse.setStatusCode(401);
            memberLoginResponse.setMessage("비밀번호가 일치하지 않습니다.");
            return memberLoginResponse;
        }

        memberLoginResponse.setStatusCode(200);
        memberLoginResponse.setMessage("로그인 성공");
        memberLoginResponse.setId(member.getId());

        return memberLoginResponse;
    }
}
