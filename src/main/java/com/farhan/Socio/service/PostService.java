package com.farhan.Socio.service;

import com.farhan.Socio.dto.CommentRequestDTO;
import com.farhan.Socio.dto.FollowRequestDTO;
import com.farhan.Socio.dto.LikeRequestDTO;
import com.farhan.Socio.dto.PostRequestDTO;
import com.farhan.Socio.entity.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    Post createPost(PostRequestDTO dto);
    Post getPostById(Long id);
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
    List<Post> getAllPosts();
    void reportPost(Long postId);
    // Follow another user
    ResponseEntity<String> followUser(FollowRequestDTO dto);

    // Unfollow another user
    ResponseEntity<String> unfollowUser(FollowRequestDTO dto);

    // Comment on a post
    ResponseEntity<String> commentOnPost(CommentRequestDTO dto);

    // Like a post
    ResponseEntity<String> likePost(LikeRequestDTO dto);

    // Unlike a post
    ResponseEntity<String> unlikePost(LikeRequestDTO dto);
}