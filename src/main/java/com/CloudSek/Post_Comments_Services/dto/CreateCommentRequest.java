package com.CloudSek.Post_Comments_Services.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCommentRequest {

    @NotBlank(message = "Content is required")
    private String content;

    private String richContent;

    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author name must not exceed 50 characters")
    private String author;

    public CreateCommentRequest() {
    }

    public CreateCommentRequest(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public CreateCommentRequest(String content, String richContent, String author) {
        this.content = content;
        this.richContent = richContent;
        this.author = author;
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

    public String getRichContent() {
        return richContent;
    }

    public void setRichContent(String richContent) {
        this.richContent = richContent;
    }
}