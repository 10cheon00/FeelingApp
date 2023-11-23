package com.feeling.app.user.controller;

import com.feeling.app.user.entity.User;
import com.feeling.app.user.service.AuthService;
import com.feeling.app.util.JwtDto;
import com.feeling.app.util.JwtProvider;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/login/jwt")
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    public AuthController(AuthService authService, JwtProvider jwtProvider) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("")
    public ResponseEntity<JwtDto> loginWithNameAndPassword(
            @RequestBody User user) {
        if (authService.validate(user.getName(), user.getPassword())) {
            return ResponseEntity
                    .ok()
                    .body(jwtProvider.createJwtDto(user.getName()));
        }
        // todo: 에러 메시지는 한 곳에서 보관하기
        throw new IllegalArgumentException("not validated");
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refreshToken(
            @RequestBody String refreshToken) throws Exception {
        try {
            jwtProvider.validate(refreshToken);
        } catch (Exception e) {
            throw new IllegalArgumentException("not validate token");
        }

        String name = jwtProvider.getSubject(refreshToken);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jwtProvider.createJwtDto(name));
    }
}
