package com.CloudSek.Post_Comments_Services.repository;

import com.CloudSek.Post_Comments_Services.entity.Comment;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface CommentRepository {
    // Find comments by post id
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

    // Find comments by author
    List<Comment> findByAuthorContainingIgnoreCase(String author);

    // Find comments by post id with pagination
    Page<Comment> findByPostIdOrderByCreatedAtDesc(Long postId, Pageable pageable);

    // Count comments by post id
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    Long countByPostId(Long postId);

    // Find recent comments
    @Query("SELECT c FROM Comment c ORDER BY c.createdAt DESC")
    List<Comment> findRecentComments(Pageable pageable);
}
