package org.example.application.services;

import org.example.domain.model.User;
import org.example.domain.repositories.UserRepository;

public class RegisterService {

    private UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String username, String password, String email) {

        if (username == null || password == null || email == null) {
            throw new IllegalArgumentException("Invalid data");
        }

        User existing = userRepository.findByUsername(username);
        if (existing != null) {
            throw new IllegalStateException("User already exists");
        }

        User user = new User(username, password, email, 0);
        userRepository.save(user);
    }
}