package com.feeling.app.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feeling.app.entity.User;
import com.feeling.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    private String usersURI = "/api/v1/users";

    @Test
    public void 유저_생성() throws Exception {
        User user = new User("username", "password");

        given(userService.createUser(any())).willReturn(user);

        mockMvc.perform(post(usersURI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }
}
