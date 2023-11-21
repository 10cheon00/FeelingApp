package com.feeling.app;

import com.feeling.app.entity.User;
import com.feeling.app.service.UserService;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FeelingAppUserTest {
    @Autowired
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    private String jwtLoginURI = "/api/v1/users/login/jwttoken";


    @Test
    public void 유저_생성() {
        User user = new User("A", "AAAAAAAA");
        User createdUser = userService.createUser(user);

        User findUser = userService.getUser(user).get();
        assertThat(findUser.getName()).isEqualTo(createdUser.getName());
    }

    @Test
    public void 중복된_유저_생성시_실패() {
        User user = new User("A", "AAAAAAAAA");
        userService.createUser(user);

        User sameNameUser = new User("A", "AAAAAAAA");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(sameNameUser);
        });
    }

    @Test
    public void 로그인_실패() throws Exception {
        User createdUser = userService.createUser(new User("A", "AAAAAAAA"));
        String wrongPassword = "BBBBBBBB";

        mockMvc.perform(post(jwtLoginURI).param("name", createdUser.getName()).param("password", wrongPassword)).andExpect(status().isBadRequest());
    }

    @Test
    public void 로그인으로_JWT_토큰_생성() throws Exception {

    }


    @Test
    public void 로그인으로_얻은_JWT_토큰_만료시간() {

    }

    @Test
    public void JWT_토큰_갱신() {

    }

    @Test
    public void 갱신시간이_만료된_JWT_토큰_갱신_실패() {

    }

}
