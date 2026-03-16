package org.example.models;

public class Like {

    private long userId;
    private long postId;

    public Like(long userId, long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public long getPostId() {
        return postId;
    }
}