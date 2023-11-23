package com.feeling.app.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class JwtProvider {
    private final String secretKey;

    private final Long verifyTokenExpiredMs;
    private final Long refreshTokenExpiredMs;

    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.verify-token.expired-ms}") Long verifyTokenExpiredMs,
            @Value("${jwt.refresh-token.expired-ms}") Long refreshTokenExpiredMs) {
        this.secretKey = secretKey;
        this.verifyTokenExpiredMs = verifyTokenExpiredMs;
        this.refreshTokenExpiredMs = refreshTokenExpiredMs;
    }

    public JwtDto createJwtDto(String name) {
        String verifyToken = createJwt(name, verifyTokenExpiredMs);
        String refreshToken = createJwt(name, refreshTokenExpiredMs);

        return new JwtDto(verifyToken, refreshToken);
    }

    public String createJwt(String name, Long expiredMs) {
        Long currentMs = new Date().getTime();

        return Jwts.builder()
                .subject(name)
                .expiration(new Timestamp(currentMs + expiredMs))
                .signWith(getSigningKey())
                .compact();
    }

    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validate(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        }
        catch (Exception e){
            throw e;
        }
    }

    public String getSubject(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }
        catch (Exception e){
            throw e;
        }
    }

    public Long getTokenExpiredMs(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .getTime();
        }
        catch (Exception e){
            throw e;
        }
    }

    public Long getVerifyTokenExpiredMs() {
        return verifyTokenExpiredMs;
    }

    public Long getRefreshTokenExpiredMs() {
        return refreshTokenExpiredMs;
    }
}
