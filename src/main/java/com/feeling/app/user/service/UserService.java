package com.feeling.app.user.service;

import com.feeling.app.user.entity.User;
import com.feeling.app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
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
}
