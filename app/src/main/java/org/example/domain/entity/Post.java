package org.example.domain.entity;

import java.time.LocalDateTime ;

public abstract class Post {

    private long id;
    private String content;
    protected long userId;
    private LocalDateTime  createdAt;
    private String authorName;
    private boolean following;
    private boolean liked;

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

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }   

    public void toggleFollowing() {
        this.following = !this.following;
    }

    public void toggleLiked() {
        this.liked = !this.liked;
    }

    public static int size() {
        // Cette méthode devrait retourner le nombre total de posts, 
        // mais comme nous n'avons pas de stockage persistant, nous allons simplement retourner 0.
        // Dans une vraie application, cela devrait interagir avec le repository pour obtenir la taille réelle.
        return 0;
    }

    public abstract long getPostId();

}