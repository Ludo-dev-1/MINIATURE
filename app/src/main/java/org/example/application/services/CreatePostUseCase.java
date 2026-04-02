package org.example.application.services;

import java.time.LocalDateTime;

import org.example.domain.entity.Comment;
import org.example.domain.entity.Post;
import org.example.domain.entity.User;
import org.example.domain.repositories.PostRepository;

public class CreatePostUseCase {
    // Le cas d'utilisation de création de post gère le processus de création d'un nouveau post.
    // Il vérifie que l'utilisateur est connecté et que le contenu du post n'est pas vide avant 
    // de créer un nouvel objet Post et de le sauvegarder dans le PostRepository.
    private final PostRepository postRepository;

    public CreatePostUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void execute(String content, User author) {
        if (author == null || content == null || content.trim().isEmpty()) {
            return;
        }

        long nextPostId = postRepository.findAll().stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0L) + 1L;
        
        Post newPost = new Comment(
                content.trim(),
                author.getUserId(),
                nextPostId,
                LocalDateTime.now(),
                author.getUsername(),
                false);

        postRepository.save(newPost);
    }
}
