package com.CloudSek.Post_Comments_Services.repository;

import com.CloudSek.Post_Comments_Services.entity.Post;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Find posts by author
    List<Post> findByAuthorContainingIgnoreCase(String author);

    // Find posts by title containing keyword
    List<Post> findByTitleContainingIgnoreCase(String keyword);

    // Find posts ordered by creation date
    List<Post> findAllByOrderByCreatedAtDesc();

    // Custom query to find posts with comment count
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments WHERE p.id = :postId")
    Post findPostWithComments(Long postId);

    // Find posts by author with pagination
    Page<Post> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
}
