package org.example.models;

import java.time.LocalDateTime ;

public class Post {

    private long id;
    private String content;
    private long userId;
    private LocalDateTime  createdAt;
    private String authorName;
    private boolean following;

    public Post(String content, long userId, long id, LocalDateTime  createdAt, String authorName, boolean following) {
        this.content = content;
        this.userId = userId;
        this.id = id;
        this.createdAt = createdAt;
        this.authorName = authorName;
        this.following = following;
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

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public void toggleFollowing() {
        this.following = !this.following;
    }

}