package com.feeling.app.user;

import com.feeling.app.controller.UserController;
import com.feeling.app.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private String usersURI = "/api/v1/users";

    @Test
    public void 유저_생성() throws Exception {
        String name = "username";
        String password = "password";
        mockMvc.perform(post(usersURI)
                .param("name", name)
                .param("password", password))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void 중복된_유저_생성시_실패() throws Exception {
        String name = "username";
        String password = "password";

        mockMvc.perform(post(usersURI)
                .param("name", name)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));

        String duplicatedName = "username";
        mockMvc.perform(post(usersURI)
                .param("name", duplicatedName)
                .param("password", password))
                .andExpect(status().isBadRequest());
    }
}
