package com.CloudSek.Post_Comments_Services.controller;

import com.CloudSek.Post_Comments_Services.dto.CommentDTO;
import com.CloudSek.Post_Comments_Services.dto.CreateCommentRequest;
import com.CloudSek.Post_Comments_Services.service.CommentService;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Create a new comment for a specific post
     */
    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId,
                                                    @Valid @RequestBody CreateCommentRequest request) {
        try {
            CommentDTO createdComment = commentService.createComment(postId, request);
            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Get a specific comment by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        try {
            CommentDTO comment = commentService.getCommentById(id);
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Get all comments for a specific post
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Update an existing comment
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id,
                                                    @Valid @RequestBody CreateCommentRequest request) {
        try {
            CommentDTO updatedComment = commentService.updateComment(id, request);
            return ResponseEntity.ok(updatedComment);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Delete a comment
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Search comments by author
     */
    @GetMapping("/search/author")
    public ResponseEntity<List<CommentDTO>> getCommentsByAuthor(@RequestParam String author) {
        try {
            List<CommentDTO> comments = commentService.getCommentsByAuthor(author);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search comments by author", e);
        }
    }

    /**
     * Get comments for a post with pagination
     */
    @GetMapping("/post/{postId}/paginated")
    public ResponseEntity<Page<CommentDTO>> getCommentsWithPagination(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<CommentDTO> comments = commentService.getCommentsWithPagination(postId, pageable);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Get comment count for a specific post
     */
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> getCommentCountByPostId(@PathVariable Long postId) {
        try {
            Long count = commentService.getCommentCountByPostId(postId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw e;
        }
    }
}
