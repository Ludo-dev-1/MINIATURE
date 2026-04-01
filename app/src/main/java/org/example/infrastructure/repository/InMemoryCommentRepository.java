package org.example.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.example.domain.database.Database;
import org.example.domain.entity.Comment;
import org.example.domain.repositories.CommentRepository;

public class InMemoryCommentRepository implements CommentRepository {

    private final Database database;

    public InMemoryCommentRepository() {
        this(InMemoryDatabase.getInstance());
    }

    public InMemoryCommentRepository(Database database) {
        this.database = database;

        if (this.database.size(Comment.class) > 0) {
            return;
        }

        // Initialisation avec quelques commentaires de test
        Comment comment1 = new Comment("Super post !", 1, 1, LocalDateTime.now().minusDays(2), "Alice", false);
        comment1.setPostId(1);
        this.database.add(Comment.class, comment1);

        Comment comment2 = new Comment("Je suis d'accord !", 2, 2, LocalDateTime.now().minusDays(1), "Bob", false);
        comment2.setPostId(1);
        this.database.add(Comment.class, comment2);

        Comment comment3 = new Comment("Merci pour le partage.", 3, 3, LocalDateTime.now().minusHours(12), "Charlie", false);
        comment3.setPostId(2);
        this.database.add(Comment.class, comment3);
    }

    @Override
    public List<Comment> findAll() {
        return database.getAll(Comment.class);
    }

    @Override
    public List<Comment> findByPostId(long postId) {
        return database.getAll(Comment.class).stream()
                .filter(c -> c.getPostId() == postId)
                .collect(Collectors.toList());
    }

    @Override
    public Comment findById(long commentId) {
        return database.findFirst(Comment.class, c -> c.getId() == commentId)
                .orElse(null);
    }

    @Override
    public void save(Comment comment) {
        long newId = database.size(Comment.class) + 1;
        comment.setId(newId);
        database.add(Comment.class, comment);
    }

    @Override
    public void delete(long commentId) {
        List<Comment> comments = database.getAll(Comment.class);
        comments.removeIf(c -> c.getId() == commentId);
    }
}
// Cette classe InMemoryCommentRepository est une implémentation de l'interface CommentRepository qui utilise une base de données en mémoire pour stocker les commentaires.
//  Elle fournit des méthodes pour trouver tous les commentaires, trouver les commentaires d'un post, trouver un commentaire par son ID, enregistrer un commentaire, et supprimer un commentaire.
// En résumé, cette classe InMemoryCommentRepository est un composant clé pour gérer les commentaires dans l'application, 
// en fournissant une implémentation concrète de CommentRepository qui utilise une base de données en mémoire pour stocker et récupérer les commentaires.
