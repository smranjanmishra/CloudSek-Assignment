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
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
    List<Comment> findByAuthorContainingIgnoreCase(String author);
    Page<Comment> findByPostIdOrderByCreatedAtDesc(Long postId, Pageable pageable);
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    Long countByPostId(@Param("postId") Long postId);
    @Query("SELECT c FROM Comment c ORDER BY c.createdAt DESC")
    List<Comment> findRecentComments(Pageable pageable);
}