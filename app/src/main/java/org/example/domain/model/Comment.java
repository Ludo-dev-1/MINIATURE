package org.example.domain.model;

import java.time.LocalDateTime;

public class Comment extends Post {
    private long postId;

    public Comment(long id, long postId, long userId, String content) {
        super(content, userId, id, LocalDateTime.now(), null, false);
        this.postId = postId;
    }

    public Comment(String content, long userId, long id, LocalDateTime createdAt, String authorName, boolean following) {
        super(content, userId, id, createdAt, authorName, following);
        this.postId = id;
    }

    public long getId() {
        return super.getId();
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return super.getUserId();
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return super.getContent();
    }

}
