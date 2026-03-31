package org.example.domain.entity;

public class Like  extends Post {

    private long postId;

    public Like(long userId, long postId) {
        super(null, userId, postId, null, null, false);
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public long getPostId() {
        return postId;
    }
}