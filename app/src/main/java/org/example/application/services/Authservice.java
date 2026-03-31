package org.example.application.services;

import org.example.domain.entity.User;
import org.example.domain.repositories.UserRepository;


public class Authservice {

    private UserRepository userRepository;

    public Authservice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
}
