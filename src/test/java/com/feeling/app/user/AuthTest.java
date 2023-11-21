//package com.feeling.app.user;
//
//import com.feeling.app.entity.User;
//import com.feeling.app.util.JwtUtil;
//import io.jsonwebtoken.Jwts;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class AuthTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    private final String jwtLoginURI = "/api/v1/users/login/jwttoken";
//
//    @Test
//    public void 로그인_실패() throws Exception {
//        User createdUser = userService.createUser(new User("A", "AAAAAAAA"));
//        String wrongName = "B";
//        String wrongPassword = "BBBBBBBB";
//
//        mockMvc.perform(post(jwtLoginURI).param("name", createdUser.getName()).param("password", wrongPassword)).andExpect(status().isBadRequest());
//
//        mockMvc.perform(post(jwtLoginURI).param("name", wrongName).param("password", createdUser.getPassword())).andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void 로그인으로_JWT_토큰_생성() throws Exception {
//        User createdUser = userService.createUser(new User("A", "AAAAAAAA"));
//
//        MvcResult result = mockMvc.perform(post(jwtLoginURI).param("name", createdUser.getName()).param("password", createdUser.getPassword())).andReturn();
//        String token = result.getResponse().getContentAsString();
//
//        String subject = Jwts.parser().verifyWith(JwtUtil.getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
//        assertThat(subject).isEqualTo(createdUser.getName());
//    }
//
//
//    @Test
//    public void 로그인으로_얻은_JWT_토큰_만료시간() {
//
//    }
//
//    @Test
//    public void JWT_토큰_갱신() {
//
//    }
//
//    @Test
//    public void 갱신시간이_만료된_JWT_토큰_갱신_실패() {
//
//    }
//}
