package org.example.application.services;

import java.util.List;

import org.example.domain.entity.Comment;
import org.example.domain.repositories.CommentRepository;

public class CommentService {
    // Dépendance au repository de commentaires
   private CommentRepository commentRepository;
    
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsForPost(long postId) {
        return commentRepository.findByPostId(postId);
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(long commentId) {
        commentRepository.delete(commentId);
    }

    
}

// Ce service de gestion des commentaires fournit des méthodes pour récupérer les commentaires d'un post, ajouter un nouveau commentaire, et supprimer un commentaire.
// La méthode getCommentsForPost filtre les commentaires pour ne retourner que ceux qui appartiennent au post spécifié par postId. 
// La méthode addComment ajoute un nouveau commentaire à la liste des commentaires, tandis que la méthode deleteComment supprime un commentaire de la liste en fonction de son ID.
// Ce service peut être utilisé dans les servlets pour gérer les opérations liées aux commentaires, telles