package org.example.domain.repositories;

import java.util.List;

import org.example.domain.entity.User;

public interface UserRepository {
    User findByUsername(String username);

    List<User> findAll();

    void save(User user);
}