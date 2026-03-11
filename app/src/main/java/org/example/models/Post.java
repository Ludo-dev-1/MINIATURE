package org.example.models;

import java.time.LocalDate;

public class Post {

    private long id;
    private String content;
    private long userId;
    private LocalDate createdAt;

    public Post(String content, long userId, long id, LocalDate createdAt) {
        this.content = content;
        this.userId = userId;
        this.id = id;
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public long getUserId() {
        return userId;
    }

    public long getId() {
        return id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}