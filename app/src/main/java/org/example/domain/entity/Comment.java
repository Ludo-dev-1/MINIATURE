package org.example.domain.entity;

import java.time.LocalDateTime;

public class Comment extends Post {
    private long postId;

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

    public String getAuthorName() {
        return super.getAuthorName();
    }

}
// Cette classe Comment étend la classe Post pour représenter un commentaire dans l'application. Elle ajoute un champ postId pour associer le commentaire à un post spécifique. Les méthodes de la classe Comment permettent d'accéder aux propriétés héritées de Post, ainsi qu'à la nouvelle propriété postId.
// Cette classe peut être utilisée pour gérer les commentaires associés aux posts dans l'application, en permettant de créer, récupérer et manipuler les commentaires de manière structurée. Par exemple, vous pouvez utiliser cette classe pour afficher les commentaires d'un post ou pour ajouter de nouveaux commentaires à un post existant.       
// En résumé, la classe Comment est un composant clé pour gérer les commentaires dans l'application, en fournissant une structure pour représenter les commentaires et leurs associations avec les posts.   

