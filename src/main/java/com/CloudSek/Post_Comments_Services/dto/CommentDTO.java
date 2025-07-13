package com.CloudSek.Post_Comments_Services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private String richContent;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long postId;
}