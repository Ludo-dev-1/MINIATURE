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

    public void setId(long id) {
        this.id = id;
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

    public abstract long getPostId();

}
// Cette classe Post est une classe abstraite qui représente un post dans l'application. Elle contient des propriétés communes à tous les types de posts, telles que le contenu, l'identifiant de l'utilisateur, la date de création, le nom de l'auteur, et des indicateurs pour savoir si l'utilisateur suit l'auteur ou a aimé le post.
// La classe Post fournit également des méthodes pour accéder et modifier ces propriétés, ainsi que des méthodes pour basculer les états de suivi et de like. La méthode size() est un placeholder qui doit être implémenté pour retourner le nombre total de posts, ce qui pourrait impliquer d'accéder à une base de données ou à un repository pour compter les posts existants.
// En résumé, la classe Post est un composant clé pour représenter les posts dans l'application, en fournissant une structure de base pour les différents types de posts (comme les commentaires et les likes) et en encapsulant les propriétés et comportements communs à tous les posts.  
