package org.example.domain.repositories;

import java.util.List;

import org.example.domain.model.Post;

public interface PostRepository {
    List<Post> findAll();   
    void save(Post post);   
}
