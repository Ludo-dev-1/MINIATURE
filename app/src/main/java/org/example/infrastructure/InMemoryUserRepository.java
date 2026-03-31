package org.example.infrastructure;

import java.util.List;

import org.example.domain.database.Database;
import org.example.domain.entity.User;
import org.example.domain.repositories.UserRepository;

public class InMemoryUserRepository implements UserRepository {

    private final Database database;

    public InMemoryUserRepository() {
        this(InMemoryDatabase.getInstance());
    }

    public InMemoryUserRepository(Database database) {
        this.database = database;

        if (this.database.size(User.class) == 0) {
            this.database.add(User.class, new User("test", "test123", "test@example.com", 1));
            this.database.add(User.class, new User("admin", "admin123", "admin@example.com", 2));
        }
    }

    @Override
    public List<User> findAll() {
        return database.getAll(User.class);
    }

    @Override
    public User findByUsername(String username) {
        return database.findFirst(User.class, u -> u.getUsername().equals(username))
                .orElse(null);
    }

    @Override
    public void save(User user) {
        user.setUserId(database.size(User.class) + 1);
        database.add(User.class, user);
    }
}