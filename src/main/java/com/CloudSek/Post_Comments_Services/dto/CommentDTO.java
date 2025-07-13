package com.CloudSek.Post_Comments_Services.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String content;
    private String richContent;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long postId;

    public CommentDTO() {
    }

    public CommentDTO(Long id, String content, String author,
                      LocalDateTime createdAt, LocalDateTime updatedAt, Long postId) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postId = postId;
    }

    public CommentDTO(Long id, String content, String richContent, String author,
                      LocalDateTime createdAt, LocalDateTime updatedAt, Long postId) {
        this.id = id;
        this.content = content;
        this.richContent = richContent;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getRichContent() {
        return richContent;
    }

    public void setRichContent(String richContent) {
        this.richContent = richContent;
    }
}
