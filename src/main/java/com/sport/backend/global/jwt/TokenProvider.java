package com.sport.backend.global.jwt;

import com.sport.backend.global.jwt.dto.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class TokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.access.expiration}")
    private long accessExpiration;

    @Value("${spring.jwt.refresh.expiration}")
    private long refreshExpiration;

    public TokenDTO createToken(String memberId) {

        long now = System.currentTimeMillis();

        String accessToken = Jwts.builder()
                .setSubject(memberId)
                .setExpiration(new Date(now + this.accessExpiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(memberId)
                .setExpiration(new Date(now + this.refreshExpiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256)
                .compact();

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            // TODO) access,refresh Token 분기 나눠서 예외처리
            return false;
        } catch (MalformedJwtException | UnsupportedJwtException | SecurityException | IllegalArgumentException e) {
            // TODO) Custom Exception으로 예외처리
            return false;
        }
    }


}