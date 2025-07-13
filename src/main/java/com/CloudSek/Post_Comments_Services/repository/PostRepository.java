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

    // Find posts by author (exact match)
    List<Post> findByAuthor(String author);
    // Find posts by author containing keyword (case insensitive)
    List<Post> findByAuthorContainingIgnoreCase(String author);
    // Find posts by title containing keyword (case insensitive)
    List<Post> findByTitleContainingIgnoreCase(String keyword);
    // Find posts ordered by creation date (descending)
    List<Post> findAllByOrderByCreatedAtDesc();
    // Custom query to find a post with its comments
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.id = :postId")
    Optional<Post> findByIdWithComments(@Param("postId") Long postId);
    // Find posts by author with pagination
    Page<Post> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    // Find posts by title with pagination
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}