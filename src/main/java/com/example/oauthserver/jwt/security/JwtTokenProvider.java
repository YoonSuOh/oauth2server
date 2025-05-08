package com.example.oauthserver.jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public String createAccessToken(String username) {
        return createToken(username, jwtProperties.getAccessTokenExpiration());
    }

    public String createRefreshToken(String username) {
        return createToken(username, jwtProperties.getRefreshTokenExpiration());
    }

    private String createToken(String username, long expireTime) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey().getBytes())
                .compact();
    }

    public String getRole(String token) {

        return Jwts.parserBuilder().setSigningKey(jwtProperties.getSecretKey().getBytes()).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtProperties.getSecretKey().getBytes()).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String getUserId(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey().getBytes())
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}