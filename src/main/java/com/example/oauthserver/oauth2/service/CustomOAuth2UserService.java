package com.example.oauthserver.oauth2.service;

import com.example.oauthserver.member.entity.Member;
import com.example.oauthserver.member.repository.MemberRepository;
import com.example.oauthserver.oauth2.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository userRepository) {

        this.memberRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oauth2 : " + oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {

            return null;
        }

        log.info("Email : " + oAuth2Response.getEmail());
        log.info("서비스 : " + oAuth2Response.getProvider());
        Member existData = memberRepository.findMemberByEmailAndProvider(oAuth2Response.getEmail(), oAuth2Response.getProvider());

        if (existData == null) {

            Member Member = new Member();
            Member.setNickname(oAuth2Response.getName());
            Member.setEmail(oAuth2Response.getEmail());
            Member.setProvider(oAuth2Response.getProvider());
            Member.setRole("ROLE_USER");

            memberRepository.save(Member);

            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setNickname(oAuth2Response.getName());
            memberDTO.setRole("ROLE_USER");

            return new CustomOAuth2User(memberDTO);
        } else {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setUserId(existData.getId());
            memberDTO.setNickname(existData.getNickname());
            memberDTO.setRole("ROLE_USER");
            log.info("CustomOAuth2UserService단에서의 id :" + memberDTO.getUserId());

            return new CustomOAuth2User(memberDTO);
        }
    }
}