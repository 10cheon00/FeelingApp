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

    private final Long expiredMs;

    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiredMs}") Long expiredMs) {
        this.secretKey = secretKey;
        this.expiredMs = expiredMs;
    }

    public String createJwt(String name) {
        return Jwts.builder()
                .subject(name)
                .expiration(new Timestamp(new Date().getTime() + expiredMs))
                .signWith(getSigningKey())
                .compact();
    }

    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
