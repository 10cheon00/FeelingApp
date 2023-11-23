package com.feeling.app.user.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtDto {
    private final String verifyToken;
    private final String refreshToken;

    @JsonCreator
    public JwtDto(
            @JsonProperty("verify_token") String verifyToken,
            @JsonProperty("refresh_token") String refreshToken) {
        this.verifyToken = verifyToken;
        this.refreshToken = refreshToken;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
