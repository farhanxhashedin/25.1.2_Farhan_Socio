package com.farhan.Socio.controller;

import com.farhan.Socio.dto.CommentRequestDTO;
import com.farhan.Socio.dto.FollowRequestDTO;
import com.farhan.Socio.dto.LikeRequestDTO;
import com.farhan.Socio.dto.PostRequestDTO;
import com.farhan.Socio.entity.Post;
import com.farhan.Socio.entity.PostStatsProjection;
import com.farhan.Socio.repository.PostRepository;
import com.farhan.Socio.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostService postService;
    @Autowired
    private final PostRepository postRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/")
    public ResponseEntity<Post> createPost(@RequestBody PostRequestDTO post) {
        return ResponseEntity.ok(postService.createPost(post));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        return ResponseEntity.ok(postService.updatePost(id, post));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/report/{postId}")
    public ResponseEntity<String> reportPost(@PathVariable Long postId) {
        postService.reportPost(postId);
        return ResponseEntity.ok("Post reported successfully");
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/follow")
    public ResponseEntity<String> followUser(@RequestBody FollowRequestDTO dto) {
        return postService.followUser(dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody FollowRequestDTO dto) {
        return postService.unfollowUser(dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/comment")
    public ResponseEntity<String> commentOnPost(@RequestBody CommentRequestDTO dto) {
        return postService.commentOnPost(dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/like")
    public ResponseEntity<String> likePost(@RequestBody LikeRequestDTO dto) {
        return postService.likePost(dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/unlike")
    public ResponseEntity<String> unlikePost(@RequestBody LikeRequestDTO dto) {
        return postService.unlikePost(dto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/stats")
    public ResponseEntity<List<PostStatsProjection>> getAllPostStats() {
        return ResponseEntity.ok(postRepository.getAllPostStats());
    }
}