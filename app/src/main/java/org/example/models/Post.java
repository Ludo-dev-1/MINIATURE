package org.example.models;

import java.time.LocalDateTime ;

public class Post {

    private long id;
    private String content;
    private long userId;
    private LocalDateTime  createdAt;
    private String authorName;

    public Post(String content, long userId, long id, LocalDateTime  createdAt, String authorName) {
        this.content = content;
        this.userId = userId;
        this.id = id;
        this.createdAt = createdAt;
        this.authorName = authorName;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}