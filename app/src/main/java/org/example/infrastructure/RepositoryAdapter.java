package org.example.infrastructure;

import java.util.List;

import org.example.domain.entity.Post;
import org.example.domain.entity.User;
import org.example.domain.repositories.PostRepository;
import org.example.domain.repositories.UserRepository;

import jakarta.servlet.ServletContext;


public final class RepositoryAdapter {

    public static final String USER_REPOSITORY_ATTRIBUTE = "userRepo";
    public static final String POST_REPOSITORY_ATTRIBUTE = "postRepo";

    
    private RepositoryAdapter() {
    }

    // Méthode pour obtenir le UserRepository depuis le contexte de la servlet
    public static UserRepository getUserRepository(ServletContext context) {
        UserRepository repository = (UserRepository) context.getAttribute(USER_REPOSITORY_ATTRIBUTE);
        if (repository == null) {
            repository = new InMemoryUserRepository();
            context.setAttribute(USER_REPOSITORY_ATTRIBUTE, repository);
        }
        syncUsersAttribute(context, repository);
        return repository;
    }

    // Méthode pour obtenir le PostRepository depuis le contexte de la servlet
    public static PostRepository getPostRepository(ServletContext context) {
        PostRepository repository = (PostRepository) context.getAttribute(POST_REPOSITORY_ATTRIBUTE);
        if (repository == null) {
            repository = new InMemoryPostRepository();
            context.setAttribute(POST_REPOSITORY_ATTRIBUTE, repository);
        }
        syncPostsAttribute(context, repository);
        return repository;
    }

    // Méthodes pour synchroniser les attributs du contexte avec les données des repositories
    private static void syncUsersAttribute(ServletContext context, UserRepository repository) {
        List<User> users = repository.findAll();
        context.setAttribute("users", users);
    }

    // Méthode pour synchroniser les posts dans le contexte
    private static void syncPostsAttribute(ServletContext context, PostRepository repository) {
        List<Post> posts = repository.findAll();
        context.setAttribute("posts", posts);
    }
}