package com.CloudSek.Post_Comments_Services.service;

import com.CloudSek.Post_Comments_Services.dto.CommentDTO;
import com.CloudSek.Post_Comments_Services.dto.CreateCommentRequest;
import org.hibernate.query.Page;

import java.awt.print.Pageable;
import java.util.List;

public class CommentService {
    CommentDTO createComment(Long postId, CreateCommentRequest request);
    CommentDTO getCommentById(Long id);
    List<CommentDTO> getCommentsByPostId(Long postId);
    CommentDTO updateComment(Long id, CreateCommentRequest request);
    void deleteComment(Long id);
    List<CommentDTO> getCommentsByAuthor(String author);
    Page<CommentDTO> getCommentsWithPagination(Long postId, Pageable pageable);
    Long getCommentCountByPostId(Long postId);
}
