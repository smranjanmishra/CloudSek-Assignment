package com.CloudSek.Post_Comments_Services.service;

import com.CloudSek.Post_Comments_Services.dto.CommentDTO;
import com.CloudSek.Post_Comments_Services.dto.CreateCommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(Long postId, CreateCommentRequest request);
    CommentDTO getCommentById(Long id);
    List<CommentDTO> getCommentsByPostId(Long postId);
    CommentDTO updateComment(Long id, CreateCommentRequest request);
    void deleteComment(Long id);
    List<CommentDTO> getCommentsByAuthor(String author);
    Page<CommentDTO> getCommentsWithPagination(Long postId, Pageable pageable);
    Long getCommentCountByPostId(Long postId);
}