package com.feeling.app.util;

import io.jsonwebtoken.Jwts;

import java.sql.Timestamp;
import java.util.Date;

public class JwtUtil {
    public static String createJwt(String name, String secretKey, Long expiredMs) {
        return Jwts.builder()
                .header()
                .keyId(secretKey)
                .and()
                .subject(name)
                .expiration(new Timestamp(new Date().getTime() + expiredMs))
                .signWith(Jwts.SIG.HS256.key().build())
                .compact();
    }
}
