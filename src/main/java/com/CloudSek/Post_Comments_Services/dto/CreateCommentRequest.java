package com.CloudSek.Post_Comments_Services.dto;

public class CreateCommentRequest {
    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author name must not exceed 50 characters")
    private String author;

    // Constructors
    public CreateCommentRequest() {}

    public CreateCommentRequest(String content, String author) {
        this.content = content;
        this.author = author;
    }

    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}
