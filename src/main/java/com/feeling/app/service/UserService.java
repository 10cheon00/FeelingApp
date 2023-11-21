package com.feeling.app.service;

import com.feeling.app.entity.User;
import com.feeling.app.repository.UserRepository;
import com.feeling.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L;

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getList() {
        return userRepository.findAll();
    }

    public User createUser(User user) throws IllegalArgumentException {
        userRepository.findByName(user.getName()).ifPresent(m -> {

            throw new IllegalArgumentException("Already Exists name");
        });
        return userRepository.save(user);
    }

    public Optional<User> getUser(User user) {
        return userRepository.findByName(user.getName());
    }

    public Optional<User> getUser(String name) {
        return userRepository.findByName(name);
    }

    public String login(String name, String password) {
        return JwtUtil.createJwt(name, secretKey, expiredMs);
    }

    public boolean validate(String name, String password) {
        Optional<User> user = userRepository.findByName(name);
        return user.filter(value -> value.getPassword().equals(password)).isPresent();
    }
}
