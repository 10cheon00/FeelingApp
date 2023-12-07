package com.feeling.app.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feeling.app.user.entity.User;
import com.feeling.app.user.util.JwtDto;
import com.feeling.app.user.util.JwtProvider;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class AuthTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtProvider jwtProvider;

    private final String usersURI = "/api/v1/users";
    private final String jwtLoginURI = "/api/v1/users/login/jwt";
    private final String jwtRefreshURI = "/api/v1/users/login/jwt/refresh";

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
        User wrongPassword = new User("A", "BBBBBBBB");
        String wrongPasswordCredential = objectMapper.writeValueAsString(wrongPassword);
        mockMvc.perform(post(jwtLoginURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongPasswordCredential))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 로그인_성공으로_JWT_토큰_획득() throws Exception {
        // login success
        MvcResult mvcResult = login();

        // verify token
        String json = mvcResult.getResponse().getContentAsString();
        JwtDto jwtDto = objectMapper.readValue(json, JwtDto.class);
        jwtProvider.validate(jwtDto.getVerifyToken());
        jwtProvider.validate(jwtDto.getRefreshToken());

        String subject = jwtProvider.getSubject(jwtDto.getVerifyToken());
        assertThat(subject).isEqualTo(user.getName());
    }

    @Test
    public void JWT_토큰_갱신() throws Exception {
        MvcResult mvcResult = login();

        String json = mvcResult.getResponse().getContentAsString();
        JwtDto jwtDto = objectMapper.readValue(json, JwtDto.class);
        Long previousExpiredMs = jwtProvider.getTokenExpiredMs(jwtDto.getVerifyToken());

        mvcResult = mockMvc.perform(post(jwtRefreshURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jwtDto.getRefreshToken()))
                .andExpect(status().isOk())
                .andReturn();

        json = mvcResult.getResponse().getContentAsString();
        jwtDto = objectMapper.readValue(json, JwtDto.class);
        jwtProvider.validate(jwtDto.getVerifyToken());
        jwtProvider.validate(jwtDto.getRefreshToken());

        // check subject of new token is same as user name
        String subject = jwtProvider.getSubject(jwtDto.getVerifyToken());
        assertThat(subject).isEqualTo(user.getName());

        // check expired ms of new tokne is greater or equal than previous token's
        Long currentExpiredMs = jwtProvider.getTokenExpiredMs(jwtDto.getVerifyToken());
        assertThat(currentExpiredMs).isGreaterThanOrEqualTo(previousExpiredMs);
    }

    @Test
    public void 만료된_JWT_토큰_갱신_실패() throws Exception {
        String expiredRefreshToken = jwtProvider.createJwt(user.getName(), -1000L);
        mockMvc.perform(post(jwtRefreshURI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expiredRefreshToken))
                .andExpect(status().isBadRequest());
    }

    public MvcResult login() throws Exception {
        return mockMvc.perform(post(jwtLoginURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCredential))
                .andExpect(status().isOk())
                .andReturn();
    }
}
