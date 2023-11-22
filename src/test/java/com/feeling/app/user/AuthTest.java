package com.feeling.app.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feeling.app.entity.User;
import com.feeling.app.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    private final String usersURI = "/api/v1/users";
    private final String jwtLoginURI = "/api/v1/users/login/jwttoken";

    @Test
    public void 로그인_실패() throws Exception {
        User user = new User("A", "AAAAAAAA");
        String userCredential = objectMapper.writeValueAsString(user);
        // create success
        mockMvc.perform(post(usersURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCredential))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(user.getName()));

        // but failed to login with wrong credential
        // failed with wrong username
        User wrongName = new User("B", "AAAAAAAA");
        String wrongNameCredential = objectMapper.writeValueAsString(wrongName);
        mockMvc.perform(post(jwtLoginURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongNameCredential))
                .andExpect(status().isBadRequest());

        // failed with wrong password
        User wrongPassword = new User("B", "AAAAAAAA");
        String wrongPasswordCredential = objectMapper.writeValueAsString(wrongPassword);
        mockMvc.perform(post(jwtLoginURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongPasswordCredential))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 로그인_성공으로_JWT_토큰_획득() throws Exception {
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
