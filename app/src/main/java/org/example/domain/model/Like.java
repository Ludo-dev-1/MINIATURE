package org.example.domain.model;

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