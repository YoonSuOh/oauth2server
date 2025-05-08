package com.example.oauthserver.jwt.service;

import com.example.oauthserver.jwt.dto.JwtLoginRequest;
import com.example.oauthserver.jwt.dto.JwtLoginResponse;
import com.example.oauthserver.jwt.entity.RefreshToken;
import com.example.oauthserver.jwt.repository.RefreshTokenRepository;
import com.example.oauthserver.jwt.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtLoginResponse jwtLoginResponse(JwtLoginRequest request){
        JwtLoginResponse jwtLoginResponse = new JwtLoginResponse();

        jwtLoginResponse.setAccessToken(jwtTokenProvider.createAccessToken(request.getId()));

        // 기존 refresh_token 확인
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(request.getId());

        String refreshToken;
        if (existingToken.isPresent()) {
            refreshToken = existingToken.get().getRefreshToken();
        } else {
            refreshToken = jwtTokenProvider.createRefreshToken(request.getId());
            refreshTokenRepository.save(RefreshToken.builder()
                    .userId(request.getId())
                    .refreshToken(refreshToken)
                    .build());
        }

        jwtLoginResponse.setRefreshToken(refreshToken);
        return jwtLoginResponse;
    }
}
