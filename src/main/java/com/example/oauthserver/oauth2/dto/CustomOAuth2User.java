package com.example.oauthserver.oauth2.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class CustomOAuth2User implements OAuth2User {

    private final MemberDTO memberDTO;

    public CustomOAuth2User(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add((GrantedAuthority) memberDTO::getRole);

        return collection;
    }

    @Override
    public String getName() {
        return memberDTO.getNickname();
    }

    public String getPhoneNumber(){ return memberDTO.getPhoneNumber();}
    public String getAddress(){ return memberDTO.getAddress();}
    public String getId() {
        log.info("CustomOAuth2User단에서의 id :" + memberDTO.getUserId());
        return memberDTO.getUserId();
    }

}
