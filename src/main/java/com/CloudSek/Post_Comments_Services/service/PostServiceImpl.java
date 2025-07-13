package com.CloudSek.Post_Comments_Services.service;

import com.CloudSek.Post_Comments_Services.dto.CommentDTO;
import com.CloudSek.Post_Comments_Services.dto.CreatePostRequest;
import com.CloudSek.Post_Comments_Services.dto.PostDTO;
import com.CloudSek.Post_Comments_Services.entity.Post;
import com.CloudSek.Post_Comments_Services.exception.PostNotFoundException;
import com.CloudSek.Post_Comments_Services.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(CreatePostRequest request) {
        try {
            Post post = new Post(request.getTitle(), request.getContent(), request.getAuthor());
            Post savedPost = postRepository.save(post);
            return convertToDTO(savedPost);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create post: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PostDTO getPostById(Long id) {
        try {
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
            return convertToDTO(post);
        }
        catch (PostNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to retrieve post: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDTO> getAllPosts() {
        try {
            List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
            return posts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to retrieve posts: " + e.getMessage(), e);
        }
    }

    @Override
    public PostDTO updatePost(Long id, CreatePostRequest request) {
        try {
            Post existingPost = postRepository.findById(id)
                    .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

            existingPost.setTitle(request.getTitle());
            existingPost.setContent(request.getContent());
            existingPost.setAuthor(request.getAuthor());

            Post updatedPost = postRepository.save(existingPost);
            return convertToDTO(updatedPost);
        }
        catch (PostNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to update post: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletePost(Long id) {
        try {
            if (!postRepository.existsById(id)) {
                throw new PostNotFoundException("Post not found with id: " + id);
            }
            postRepository.deleteById(id);
        }
        catch (PostNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to delete post: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDTO> getPostsByAuthor(String author) {
        try {
            List<Post> posts = postRepository.findByAuthorContainingIgnoreCase(author);
            return posts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to retrieve posts by author: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDTO> searchPostsByTitle(String keyword) {
        try {
            List<Post> posts = postRepository.findByTitleContainingIgnoreCase(keyword);
            return posts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to search posts by title: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDTO> getPostsWithPagination(Pageable pageable) {
        try {
            Page<Post> posts = postRepository.findAll(pageable);
            return posts.map(this::convertToDTO);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to retrieve posts with pagination: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PostDTO getPostWithComments(Long id) {
        try {
            Post post = postRepository.findByIdWithComments(id)
                    .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
            return convertToDTOWithComments(post);
        }
        catch (PostNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to retrieve post with comments: " + e.getMessage(), e);
        }
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                null  // Collections.emptyList()
        );
    }

    private PostDTO convertToDTOWithComments(Post post) {
        PostDTO dto = convertToDTO(post);
        if (post.getComments() != null) {
            List<CommentDTO> commentDTOs = post.getComments().stream()
                    .map(comment -> new CommentDTO(
                            comment.getId(),
                            comment.getContent(),
                            comment.getRichContent(),
                            comment.getAuthor(),
                            comment.getCreatedAt(),
                            comment.getUpdatedAt(),
                            comment.getPost().getId()
                    ))
                    .collect(Collectors.toList());
            dto.setComments(commentDTOs);
        }
        return dto;
    }
}