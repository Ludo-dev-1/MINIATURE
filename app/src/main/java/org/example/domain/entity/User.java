package org.example.domain.entity;

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
// Cette classe User représente un utilisateur dans l'application. Elle contient des propriétés telles que le nom d'utilisateur, le mot de passe, l'email, et des listes pour les utilisateurs suivis et les posts aimés. Les méthodes de la classe permettent de gérer les actions de suivi et de like, ainsi que d'accéder et de modifier les propriétés de l'utilisateur.
// Cette classe peut être utilisée pour gérer les utilisateurs dans l'application, en permettant de créer, récupérer et manipuler les utilisateurs de manière structurée. Par exemple, vous pouvez utiliser cette classe pour afficher les informations d'un utilisateur, pour permettre à un utilisateur de suivre ou de ne plus suivre un autre utilisateur, ou pour permettre à un utilisateur d'aimer ou de ne plus aimer un post existant.       
// En résumé, la classe User est un composant clé pour représenter les utilisateurs dans l'application, en fournissant une structure pour gérer les propriétés et les actions des utilisateurs de manière organisée.    
