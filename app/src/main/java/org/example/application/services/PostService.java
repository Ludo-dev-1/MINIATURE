package org.example.application.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.example.domain.entity.Comment;
import org.example.domain.entity.Post;
import org.example.domain.entity.User;

public class PostService {
    private List<Post> posts;

    public PostService(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPostsForUser(User user, List<User> allUsers) {
        for (Post post : posts) {
            User author = allUsers.stream()
                                  .filter(u -> u.getUserId() == post.getUserId())
                                  .findFirst()
                                  .orElse(null);
            if (author != null) post.setAuthorName(author.getUsername());

            post.setFollowing(user.getFollowing().contains(post.getUserId()));
            post.setLiked(user.getLiked().contains(post.getId()));
        }
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        return posts;
    }

    public void addPost(String content, User author) {
        if (content != null && !content.trim().isEmpty()) {
            posts.add(new Comment(content, author.getUserId(), posts.size() + 1,
                                  LocalDateTime.now(), author.getUsername(), false));
        }
    }
}