package com.CloudSek.Post_Comments_Services.controller;

import com.CloudSek.Post_Comments_Services.dto.CommentDTO;
import com.CloudSek.Post_Comments_Services.dto.CreateCommentRequest;
import com.CloudSek.Post_Comments_Services.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private CommentService commentService;

    // Ek specific post ke liye naya comment banana he
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

    // ID ke zariye ek specific comment pana he
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        try {
            CommentDTO comment = commentService.getCommentById(id);
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            throw e;
        }
    }

    // Ek specific post ke saare comments pana he
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            throw e;
        }
    }

    // Ek existing comment ko update karna he
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

    // Ek comment ko delete karna he
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        }
    }

    // Author ke naam se comments dhundna he
    @GetMapping("/search/author")
    public ResponseEntity<List<CommentDTO>> getCommentsByAuthor(@RequestParam String author) {
        try {
            List<CommentDTO> comments = commentService.getCommentsByAuthor(author);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search comments by author", e);
        }
    }

    // Ek post ke comments pagination ke saath pana he
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

    // Ek specific post ke liye comment count milega
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