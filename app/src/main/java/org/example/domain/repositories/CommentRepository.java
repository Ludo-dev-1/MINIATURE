package org.example.domain.repositories;

import java.util.List;

import org.example.domain.entity.Comment;

public interface CommentRepository {
    List<Comment> findAll();

    List<Comment> findByPostId(long postId);

    Comment findById(long commentId);

    void save(Comment comment);

    void delete(long commentId);
}
