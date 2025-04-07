package com.example.oauthserver.jwt.controller;

import com.example.oauthserver.jwt.security.JwtTokenProvider;
import com.example.oauthserver.jwt.entity.RefreshToken;
import com.example.oauthserver.jwt.repository.RefreshTokenRepository;
import com.example.oauthserver.member.dto.MemberLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequest request) {
        String accessToken = jwtTokenProvider.createAccessToken(request.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(request.getId());

        // 기존 토큰 있으면 삭제 (중복 방지)
        refreshTokenRepository.deleteByUserId(request.getId());

        // 새로운 토큰 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .userId(request.getId())
                .refreshToken(refreshToken)
                .build());

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        String userId = jwtTokenProvider.getUserId(refreshToken);

        RefreshToken saved = refreshTokenRepository.findByUserId(userId).orElse(null);
        if (saved != null && saved.getRefreshToken().equals(refreshToken)) {
            String newAccessToken = jwtTokenProvider.createAccessToken(userId);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
