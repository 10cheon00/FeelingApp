package com.feeling.app.controller;

import com.feeling.app.entity.User;
import com.feeling.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @Controller는 View를 반환한다. 그런데 Data만 반환해야할 때가 있다.
// 그럴 때 함수에 @ResponseBody를 사용한다.
// 귀찮으니까, Data만 반환할 컨트롤러는 @RestController만 써도 된다.
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getUserList() {
        return userService.getList();
    }
}
