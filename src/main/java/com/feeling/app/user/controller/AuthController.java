package com.feeling.app.user.controller;

import com.feeling.app.user.entity.User;
import com.feeling.app.user.exception.IllegalUserCredentialException;
import com.feeling.app.user.exception.NotValidateJwtException;
import com.feeling.app.user.service.AuthService;
import com.feeling.app.user.service.UserService;
import com.feeling.app.user.util.JwtDto;
import com.feeling.app.user.util.JwtProvider;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.url}/users/login/jwt")
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    public AuthController(AuthService authService, JwtProvider jwtProvider) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("")
    public ResponseEntity<JwtDto> loginWithNameAndPassword(
            @RequestBody User user) throws Exception {
        if (!authService.validate(user.getName(), user.getPassword())) {
            throw new IllegalUserCredentialException(user);
        }

        return ResponseEntity
                .ok()
                .body(jwtProvider.createJwtDto(user.getName()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refreshToken(
            @RequestBody String refreshToken) throws Exception {
        try {
            jwtProvider.validate(refreshToken);
        } catch (Exception e) {
            throw new NotValidateJwtException();
        }

        String name = jwtProvider.getSubject(refreshToken);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jwtProvider.createJwtDto(name));
    }
}
