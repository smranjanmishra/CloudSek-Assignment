package com.CloudSek.Post_Comments_Services.repository;

import com.CloudSek.Post_Comments_Services.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Find comments by post id
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

    // Find comments by author
    List<Comment> findByAuthorContainingIgnoreCase(String author);

    // Find comments by post id with pagination
    Page<Comment> findByPostIdOrderByCreatedAtDesc(Long postId, Pageable pageable);

    // Count comments by post id
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    Long countByPostId(@Param("postId") Long postId);

    // Find recent comments
    @Query("SELECT c FROM Comment c ORDER BY c.createdAt DESC")
    List<Comment> findRecentComments(Pageable pageable);
}