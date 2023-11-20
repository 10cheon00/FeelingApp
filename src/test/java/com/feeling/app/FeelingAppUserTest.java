package com.feeling.app;

import com.feeling.app.entity.User;
import com.feeling.app.repository.UserRepository;
import com.feeling.app.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class FeelingAppUserTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void 유저_생성(){
        User user = new User("A", "AAAAAAAA");
        User createdUser = userService.createUser(user);

        User findUser = userService.getUser(user).get();
        assertThat(findUser.getName()).isEqualTo(createdUser.getName());
    }

    @Test
    public void 중복된_유저_생성시_실패() {

    }
}
