package com.CloudSek.Post_Comments_Services.service;

import com.CloudSek.Post_Comments_Services.dto.CommentDTO;
import com.CloudSek.Post_Comments_Services.dto.CreateCommentRequest;
import com.CloudSek.Post_Comments_Services.entity.Comment;
import com.CloudSek.Post_Comments_Services.entity.Post;
import com.CloudSek.Post_Comments_Services.exception.CommentNotFoundException;
import com.CloudSek.Post_Comments_Services.exception.PostNotFoundException;
import com.CloudSek.Post_Comments_Services.repository.CommentRepository;
import com.CloudSek.Post_Comments_Services.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDTO createComment(Long postId, CreateCommentRequest request) {
        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

            Comment comment = new Comment(request.getContent(), request.getAuthor());
            comment.setPost(post);

            Comment savedComment = commentRepository.save(comment);
            return convertToDTO(savedComment);
        } catch (PostNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create comment: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDTO getCommentById(Long id) {
        try {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
            return convertToDTO(comment);
        } catch (CommentNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve comment: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        try {
            // Verify post exists
            if (!postRepository.existsById(postId)) {
                throw new PostNotFoundException("Post not found with id: " + postId);
            }

            List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
            return comments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (PostNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve comments: " + e.getMessage(), e);
        }
    }

    @Override
    public CommentDTO updateComment(Long id, CreateCommentRequest request) {
        try {
            Comment existingComment = commentRepository.findById(id)
                    .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));

            existingComment.setContent(request.getContent());
            existingComment.setAuthor(request.getAuthor());

            Comment updatedComment = commentRepository.save(existingComment);
            return convertToDTO(updatedComment);
        } catch (CommentNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update comment: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteComment(Long id) {
        try {
            if (!commentRepository.existsById(id)) {
                throw new CommentNotFoundException("Comment not found with id: " + id);
            }
            commentRepository.deleteById(id);
        } catch (CommentNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete comment: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByAuthor(String author) {
        try {
            List<Comment> comments = commentRepository.findByAuthorContainingIgnoreCase(author);
            return comments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve comments by author: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDTO> getCommentsWithPagination(Long postId, Pageable pageable) {
        try {
            // Verify post exists
            if (!postRepository.existsById(postId)) {
                throw new PostNotFoundException("Post not found with id: " + postId);
            }

            Page<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId, pageable);
            return comments.map(this::convertToDTO);
        } catch (PostNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve comments with pagination: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCommentCountByPostId(Long postId) {
        try {
            // Verify post exists
            if (!postRepository.existsById(postId)) {
                throw new PostNotFoundException("Post not found with id: " + postId);
            }

            return commentRepository.countByPostId(postId);
        } catch (PostNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to count comments: " + e.getMessage(), e);
        }
    }

    // Helper method to convert Comment entity to DTO
    private CommentDTO convertToDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getPost().getId()
        );
    }
}