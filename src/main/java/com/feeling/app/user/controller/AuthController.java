package com.feeling.app.user.controller;

import com.feeling.app.user.entity.User;
import com.feeling.app.user.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/login/jwt")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public ResponseEntity<String> loginWithNameAndPassword(
            @RequestBody User user) {
        if (authService.validate(user.getName(), user.getPassword())) {
            return ResponseEntity
                    .ok()
                    .body(authService.login(user.getName(), user.getPassword()));
        }
        // todo: 에러 메시지는 한 곳에서 보관하기
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("Not validated credential.");
    }
}
