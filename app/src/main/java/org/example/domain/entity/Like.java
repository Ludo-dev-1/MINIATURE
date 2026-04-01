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
// Cette classe Like étend la classe Post pour représenter un "like" dans l'application. Elle ajoute un champ postId pour associer le like à un post spécifique. Les méthodes de la classe Like permettent d'accéder aux propriétés héritées de Post, ainsi qu'à la nouvelle propriété postId.
// Cette classe peut être utilisée pour gérer les likes associés aux posts dans l'application, en permettant de créer, récupérer et manipuler les likes de manière structurée. Par exemple, vous pouvez utiliser cette classe pour afficher le nombre de likes d'un post ou pour permettre à un utilisateur d'aimer ou de ne plus aimer un post existant.       
// En résumé, la classe Like est un composant clé pour gérer les likes dans l'application, en fournissant une structure pour représenter les likes et leurs associations avec les posts.    
