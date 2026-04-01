package org.example.application.usecase;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.domain.entity.Post;
import org.example.domain.entity.User;
import org.example.domain.repositories.PostRepository;
import org.example.domain.repositories.UserRepository;

public class GetFeedUseCase {
    // Le cas d'utilisation de récupération du feed gère le processus de récupération des posts pour l'affichage du feed.
    //  Il récupère tous les posts et les utilisateurs, puis associe les noms d'auteur aux posts et détermine si l'utilisateur courant suit les auteurs des posts et s'il a aimé les posts.
    //  Enfin, il trie les posts par date de création avant de les retourner.
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public GetFeedUseCase(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> execute(User currentUser) {
        List<Post> posts = postRepository.findAll();
        List<User> users = userRepository.findAll();

        Map<Long, String> usernameById = new HashMap<>();
        for (User user : users) {
            usernameById.put(user.getUserId(), user.getUsername());
        }

        for (Post post : posts) {
            post.setAuthorName(usernameById.getOrDefault(post.getUserId(), post.getAuthorName()));

            if (currentUser != null) {
                post.setFollowing(currentUser.getFollowing().contains(post.getUserId()));
                post.setLiked(currentUser.getLiked().contains(post.getId()));
            } else {
                post.setFollowing(false);
                post.setLiked(false);
            }
        }

        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        return posts;
    }
}
