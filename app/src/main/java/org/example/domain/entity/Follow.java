package org.example.domain.entity;

public class Follow {
    private long followerId;
    private long followingId;
    private boolean isFollowing = false;

    public Follow(long followerId, long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
        this.isFollowing = false;
    }

    public long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }

    public long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(long followingId) {
        this.followingId = followingId;
    }

    public boolean isFollowing() {
        return isFollowing; 
    }

    public void toggleFollow() {
        isFollowing = !isFollowing;
    }


    @Override
    public String toString() {
        return "Follow [followerId=" + followerId + ", followingId=" + followingId + ", isFollowing=" + isFollowing + "]";
    }

}
// Cette classe Follow représente une relation de suivi entre deux utilisateurs dans l'application. Elle contient les identifiants du follower et du following, ainsi qu'un indicateur pour savoir si le follower suit actuellement le following. La méthode toggleFollow permet de basculer l'état de suivi entre les deux utilisateurs.
// Cette classe peut être utilisée pour gérer les relations de suivi dans l'application, en permettant de créer, récupérer et manipuler les relations de suivi de manière structurée. Par exemple, vous pouvez utiliser cette classe pour afficher les utilisateurs suivis par un utilisateur ou pour permettre à un utilisateur de suivre ou de ne plus suivre un autre utilisateur.       
// En résumé, la classe Follow est un composant clé pour gérer les relations de suivi dans l'application, en fournissant une structure pour représenter les relations de suivi entre les utilisateurs.      
