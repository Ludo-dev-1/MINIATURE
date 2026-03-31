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
