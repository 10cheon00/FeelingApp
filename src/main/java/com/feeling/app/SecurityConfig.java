package com.feeling.app;

import com.feeling.app.user.util.JwtProvider;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }
}
