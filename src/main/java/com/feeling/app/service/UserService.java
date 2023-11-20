package com.feeling.app.service;

import com.feeling.app.entity.User;
import com.feeling.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
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
}
