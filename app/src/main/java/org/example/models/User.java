package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long userId;
    private String username;
    private String password;
    private String email;
    private List<Long> following = new ArrayList<>();
    private List<Long> liked = new ArrayList<>();

    public User(String username, String password, String email, long userId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Long> getFollowing() {
        return following;
    }

    public List<Long> getLiked() {
        return liked;
    }

    public void like(long postId) {
        if (!liked.contains(postId)) {
            liked.add(postId);
        }
    }

    public void unlike(long postId) {
        liked.remove(Long.valueOf(postId));
    }

    public void follow(long userId) {
        if (!following.contains(userId)) {
            following.add(userId);
        }
    }

    public void unfollow(long userId) {
        following.remove(userId);
    }

    @Override
    public String toString() {

        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userId=" + userId +
                '}';
    }
}
