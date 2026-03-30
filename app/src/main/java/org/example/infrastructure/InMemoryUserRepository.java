package org.example.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.example.domain.model.User;
import org.example.domain.repositories.UserRepository;

public class InMemoryUserRepository implements UserRepository {

    private List<User> users = new ArrayList<>();

    public InMemoryUserRepository() {
        users.add(new User("test", "test123", "test@example.com", 1));
        users.add(new User("admin", "admin123", "admin@example.com", 2));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(User user) {
        user.setUserId(users.size() + 1);
        users.add(user);
    }
}