package org.example.domain.repositories;

import java.util.List;

import org.example.domain.entity.Post;

public interface PostRepository {
    List<Post> findAll();   
    void save(Post post);
    public static Object getInstance() {
        return null;
    }   
}
// Cette interface PostRepository définit les opérations de base pour gérer les posts dans l'application. Elle fournit des méthodes pour trouver tous les posts et pour enregistrer un post. La méthode getInstance() est un placeholder qui pourrait être utilisée pour implémenter un pattern de singleton ou pour obtenir une instance spécifique du repository, selon les besoins de l'application.
// En résumé, cette interface PostRepository est un composant clé pour gérer les posts dans l'application, en fournissant une structure pour effectuer des opérations courantes sur les posts, telles que la récupération de tous les posts et l'enregistrement de nouveaux posts.  
