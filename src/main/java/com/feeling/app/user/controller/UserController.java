package com.feeling.app.user.controller;

import com.feeling.app.user.entity.User;
import com.feeling.app.user.service.AuthService;
import com.feeling.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

// @Controller는 View를 반환한다. 그런데 Data만 반환해야할 때가 있다.
// 그럴 때 함수에 @ResponseBody를 사용한다.
// 귀찮으니까, Data만 반환할 컨트롤러는 @RestController만 써도 된다.
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    /**
     * db testing
     * TODO: this method should be removed.
     */
    @GetMapping("")
    public List<User> getUserList() {
        return userService.getList();
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(
            @RequestBody User user
    ) throws Exception {
        if(userService.getUser(user).isPresent()) {
            throw new IllegalArgumentException("Already exists username.");
        }
        return ResponseEntity
                .created(URI.create("/api/v1/users"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.createUser(user));
    }

    @PostMapping("/login/jwttoken")
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
