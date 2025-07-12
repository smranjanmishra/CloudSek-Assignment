package com.CloudSek.Post_Comments_Services.controller;

import com.CloudSek.Post_Comments_Services.dto.CreatePostRequest;
import com.CloudSek.Post_Comments_Services.dto.PostDTO;
import com.CloudSek.Post_Comments_Services.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Create a new post
     */
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody CreatePostRequest request) {
        try {
            PostDTO createdPost = postService.createPost(request);
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create post", e);
        }
    }

    /**
     * Get all posts
     */
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        try {
            List<PostDTO> posts = postService.getAllPosts();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve posts", e);
        }
    }

    /**
     * Get a specific post by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        try {
            PostDTO post = postService.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve post with id: " + id, e);
        }
    }

    /**
     * Get a post with all its comments
     */
    @GetMapping("/{id}/with-comments")
    public ResponseEntity<PostDTO> getPostWithComments(@PathVariable Long id) {
        try {
            PostDTO post = postService.getPostWithComments(id);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve post with comments for id: " + id, e);
        }
    }

    /**
     * Update an existing post
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id,
                                              @Valid @RequestBody CreatePostRequest request) {
        try {
            PostDTO updatedPost = postService.updatePost(id, request);
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update post with id: " + id, e);
        }
    }

    /**
     * Delete a post
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete post with id: " + id, e);
        }
    }

    /**
     * Search posts by author
     */
    @GetMapping("/search/author")
    public ResponseEntity<List<PostDTO>> getPostsByAuthor(@RequestParam String author) {
        try {
            List<PostDTO> posts = postService.getPostsByAuthor(author);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search posts by author", e);
        }
    }

    /**
     * Search posts by title keyword
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<PostDTO>> searchPostsByTitle(@RequestParam String keyword) {
        try {
            List<PostDTO> posts = postService.searchPostsByTitle(keyword);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search posts by title", e);
        }
    }

    /**
     * Get posts with pagination
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<PostDTO>> getPostsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<PostDTO> posts = postService.getPostsWithPagination(pageable);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve posts with pagination", e);
        }
    }
}