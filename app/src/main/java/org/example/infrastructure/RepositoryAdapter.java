package org.example.infrastructure;

import java.util.List;

import org.example.domain.model.Post;
import org.example.domain.model.User;
import org.example.domain.repositories.PostRepository;
import org.example.domain.repositories.UserRepository;

import jakarta.servlet.ServletContext;

public final class RepositoryAdapter {

    public static final String USER_REPOSITORY_ATTRIBUTE = "userRepo";
    public static final String POST_REPOSITORY_ATTRIBUTE = "postRepo";

    private RepositoryAdapter() {
    }

    public static UserRepository getUserRepository(ServletContext context) {
        UserRepository repository = (UserRepository) context.getAttribute(USER_REPOSITORY_ATTRIBUTE);
        if (repository == null) {
            repository = new InMemoryUserRepository();
            context.setAttribute(USER_REPOSITORY_ATTRIBUTE, repository);
        }
        syncUsersAttribute(context, repository);
        return repository;
    }

    public static PostRepository getPostRepository(ServletContext context) {
        PostRepository repository = (PostRepository) context.getAttribute(POST_REPOSITORY_ATTRIBUTE);
        if (repository == null) {
            repository = new InMemoryPostRepository();
            context.setAttribute(POST_REPOSITORY_ATTRIBUTE, repository);
        }
        syncPostsAttribute(context, repository);
        return repository;
    }

    private static void syncUsersAttribute(ServletContext context, UserRepository repository) {
        List<User> users = repository.findAll();
        context.setAttribute("users", users);
    }

    private static void syncPostsAttribute(ServletContext context, PostRepository repository) {
        List<Post> posts = repository.findAll();
        context.setAttribute("posts", posts);
    }
}