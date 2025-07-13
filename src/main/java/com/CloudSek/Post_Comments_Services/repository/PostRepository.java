package com.CloudSek.Post_Comments_Services.repository;

import com.CloudSek.Post_Comments_Services.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(String author);
    List<Post> findByAuthorContainingIgnoreCase(String author);
    List<Post> findByTitleContainingIgnoreCase(String keyword);
    List<Post> findAllByOrderByCreatedAtDesc();
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.id = :postId")
    Optional<Post> findByIdWithComments(@Param("postId") Long postId);
    Page<Post> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}