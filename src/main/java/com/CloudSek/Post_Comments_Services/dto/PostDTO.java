package com.CloudSek.Post_Comments_Services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDTO> comments;
}