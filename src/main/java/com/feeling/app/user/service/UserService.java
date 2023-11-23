package com.feeling.app.user.service;

import com.feeling.app.user.entity.User;
import com.feeling.app.user.repository.UserRepository;
import com.feeling.app.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
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
        return jwtProvider.createJwt(name);
    }

    public boolean validate(String name, String password) {
        Optional<User> user = userRepository.findByName(name);
        return user.filter(value -> value.getPassword().equals(password)).isPresent();
    }
}
