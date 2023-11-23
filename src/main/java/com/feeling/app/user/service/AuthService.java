package com.feeling.app.user.service;

import com.feeling.app.user.entity.User;
import com.feeling.app.user.repository.UserRepository;
import com.feeling.app.util.JwtDto;
import com.feeling.app.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthService(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    public JwtDto login(String name, String password) {
        return jwtProvider.createJwt(name);
    }

    public boolean validate(String name, String password) {
        Optional<User> user = userRepository.findByName(name);
        return user.filter(value -> value.getPassword().equals(password)).isPresent();
    }
}
