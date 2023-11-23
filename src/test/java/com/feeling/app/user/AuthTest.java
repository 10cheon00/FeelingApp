package com.feeling.app.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feeling.app.user.entity.User;
import com.feeling.app.util.JwtDto;
import com.feeling.app.util.JwtProvider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class AuthTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtProvider jwtProvider;

    private final String usersURI = "/api/v1/users";
    private final String jwtLoginURI = "/api/v1/users/login/jwt";

    ObjectMapper objectMapper;
    private final User user;
    private final String userCredential;

    public AuthTest() throws Exception {
         objectMapper = new ObjectMapper();
         user = new User("A", "AAAAAAAA");
         userCredential = objectMapper.writeValueAsString(user);
    }

    @BeforeEach
    public void 유저_생성() throws Exception {
        // user create success
        mockMvc.perform(post(usersURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCredential))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void 로그인_실패() throws Exception {
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
        // login success
        MvcResult mvcResult = mockMvc.perform(post(jwtLoginURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCredential))
                .andExpect(status().isOk())
                .andReturn();

        // verify token
        String json = mvcResult.getResponse().getContentAsString();
        JwtDto jwtDto = objectMapper.readValue(json, JwtDto.class);
        jwtProvider.validate(jwtDto.getVerifyToken());
        jwtProvider.validate(jwtDto.getRefreshToken());

        String subject = jwtProvider.getSubject(jwtDto.getVerifyToken());
        assertThat(subject).isEqualTo(user.getName());
    }

    @Test
    public void 로그인으로_얻은_JWT_토큰_만료() throws Exception {
        // login success
        MvcResult mvcResult = mockMvc.perform(post(jwtLoginURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCredential))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        JwtDto jwtDto = objectMapper.readValue(json, JwtDto.class);
        jwtProvider.validate(jwtDto.getVerifyToken());

        Long expiredMs = jwtProvider.getTokenExpiredMs(jwtDto.getVerifyToken());
        Long expiredTime = new Date().getTime() + jwtProvider.getVerifyTokenExpiredMs();
        assertThat(expiredMs).isLessThan(expiredTime);

        // just print for watch expired value
        System.out.printf("VerifyToken expired at %s, %d\n", new Date(expiredMs), expiredMs);

        expiredMs = jwtProvider.getTokenExpiredMs(jwtDto.getRefreshToken());
        expiredTime = new Date().getTime() + jwtProvider.getRefreshTokenExpiredMs();
        assertThat(expiredMs).isLessThan(expiredTime);

        // just print for watch expired value
        System.out.printf("RefreshToken expired at %s, %d\n", new Date(expiredMs), expiredMs);
    }

    @Test
    public void JWT_토큰_갱신() {

    }

    @Test
    public void 갱신시간이_만료된_JWT_토큰_갱신_실패() {

    }
}
