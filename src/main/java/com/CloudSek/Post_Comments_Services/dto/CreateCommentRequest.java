package com.CloudSek.Post_Comments_Services.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

    @NotBlank(message = "Content is required")
    private String content;

    private String richContent;

    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author name must not exceed 50 characters")
    private String author;
}