package com.CloudSek.Post_Comments_Services.service;

import com.CloudSek.Post_Comments_Services.dto.CreatePostRequest;
import com.CloudSek.Post_Comments_Services.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDTO createPost(CreatePostRequest request);
    PostDTO getPostById(Long id);
    List<PostDTO> getAllPosts();
    PostDTO updatePost(Long id, CreatePostRequest request);
    void deletePost(Long id);
    List<PostDTO> getPostsByAuthor(String author);
    List<PostDTO> searchPostsByTitle(String keyword);
    Page<PostDTO> getPostsWithPagination(Pageable pageable);
    PostDTO getPostWithComments(Long id);
}