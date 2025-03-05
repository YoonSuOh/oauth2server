package com.example.oauthserver.member.controller;

import com.example.oauthserver.common.Response;
import com.example.oauthserver.member.dto.MemberJoinRequest;
import com.example.oauthserver.member.dto.MemberJoinResponse;
import com.example.oauthserver.member.dto.MemberLoginRequest;
import com.example.oauthserver.member.dto.MemberLoginResponse;
import com.example.oauthserver.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/members/")
public class MemberRestController {
    private final MemberService memberService;

    /* 회원가입 진행 */
    @PostMapping("/join")
    public Response<MemberJoinResponse> join(@RequestBody MemberJoinRequest memberJoinRequest){
        MemberJoinResponse memberJoinResponse = memberService.join(memberJoinRequest);
        if(memberJoinResponse.getStatusCode() == 401){
            return Response.error(memberJoinResponse);
        }
        else {
            return Response.success(memberJoinResponse);
        }
    }

    /* 로그인 */
    @PostMapping("/login")
    public Response<MemberLoginResponse> login(@RequestBody MemberLoginRequest memberLoginRequest){
        MemberLoginResponse memberLoginResponse = memberService.login(memberLoginRequest);
        if(memberLoginResponse.getStatusCode() == 401){
            return Response.error(memberLoginResponse);
        }
        else {
            return Response.success(memberLoginResponse);
        }
    }
}
