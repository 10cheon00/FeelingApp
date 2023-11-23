package com.feeling.app.user.service;

import com.feeling.app.user.entity.User;
import com.feeling.app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validate(String name, String password) {
        Optional<User> user = userRepository.findByName(name);
        return user.filter(value -> value.getPassword().equals(password)).isPresent();
    }
}
