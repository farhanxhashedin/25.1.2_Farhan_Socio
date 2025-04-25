package com.farhan.Socio.service;

import com.farhan.Socio.dto.CommentRequestDTO;
import com.farhan.Socio.dto.FollowRequestDTO;
import com.farhan.Socio.dto.LikeRequestDTO;
import com.farhan.Socio.dto.PostRequestDTO;
import com.farhan.Socio.entity.*;
import com.farhan.Socio.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserRelationRepository userRelationRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final PostLikeRepository postLikeRepository;

    @Override
    public Post createPost(PostRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post();
        post.setUser(user);
        post.setContent(dto.getContent());
        post.setFileUrl(dto.getFileUrl());
        post.setFileType(dto.getFileType());

        if (dto.getSharedFromPostId() != null) {
            Post sharedFrom = postRepository.findById(dto.getSharedFromPostId())
                    .orElseThrow(() -> new RuntimeException("Shared post not found"));
            post.setSharedFrom(sharedFrom);
        }

        return postRepository.save(post);
    }


    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public Post updatePost(Long id, Post updatedPost) {
        Post post = postRepository.findById(id).get();
        post.setContent(updatedPost.getContent());
        post.setFileUrl(updatedPost.getFileUrl());
        post.setFileType(updatedPost.getFileType());
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void reportPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setReported(true);
        postRepository.save(post);
    }

    @Override
    public ResponseEntity<String> followUser(FollowRequestDTO dto) {
        if (dto.getSourceUserId().equals(dto.getTargetUserId())) {
            return ResponseEntity.badRequest().body("Cannot follow yourself.");
        }

        User sourceUser = userRepository.findById(dto.getSourceUserId()).orElse(null);
        User targetUser = userRepository.findById(dto.getTargetUserId()).orElse(null);

        if (sourceUser == null || targetUser == null) {
            return ResponseEntity.badRequest().body("User(s) not found.");
        }

        UserRelation existingRelation = userRelationRepository.findBySourceUserIdAndTargetUserId(dto.getSourceUserId(), dto.getTargetUserId()).orElse(null);

        if (existingRelation != null && existingRelation.getRelationType() == RelationType.FOLLOW) {
            return ResponseEntity.badRequest().body("Already following.");
        }

        if (existingRelation == null) {
            existingRelation = new UserRelation();
        }
        existingRelation.setSourceUser(sourceUser);
        existingRelation.setTargetUser(targetUser);
        existingRelation.setRelationType(RelationType.FOLLOW);
        userRelationRepository.save(existingRelation);

        return ResponseEntity.ok("Followed successfully.");
    }

    // Unfollow user
    @Override
    public ResponseEntity<String> unfollowUser(FollowRequestDTO dto) {
        UserRelation existingRelation = userRelationRepository.findBySourceUserIdAndTargetUserId(dto.getSourceUserId(), dto.getTargetUserId()).orElse(null);

        if (existingRelation == null || existingRelation.getRelationType() != RelationType.FOLLOW) {
            return ResponseEntity.badRequest().body("Not following this user.");
        }

        userRelationRepository.delete(existingRelation);
        return ResponseEntity.ok("Unfollowed successfully.");
    }

    // Comment on post
    @Override
    public ResponseEntity<String> commentOnPost(CommentRequestDTO dto) {
        Post post = postRepository.findById(dto.getPostId()).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        if (post == null || user == null) {
            return ResponseEntity.badRequest().body("Post or user not found.");
        }

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(dto.getContent());
        commentRepository.save(comment);

        return ResponseEntity.ok("Comment added.");
    }

    // Like post
    @Override
    public ResponseEntity<String> likePost(LikeRequestDTO dto) {
        Post post = postRepository.findById(dto.getPostId()).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        if (post == null || user == null) {
            return ResponseEntity.badRequest().body("Post or user not found.");
        }

        PostLike existingLike = postLikeRepository.findByPostIdAndUserId(dto.getPostId(), dto.getUserId()).orElse(null);
        if (existingLike != null) {
            return ResponseEntity.badRequest().body("Post already liked.");
        }

        PostLike like = new PostLike();
        like.setPost(post);
        like.setUser(user);
        postLikeRepository.save(like);

        return ResponseEntity.ok("Post liked.");
    }

    // Unlike post
    @Override
    public ResponseEntity<String> unlikePost(LikeRequestDTO dto) {
        PostLike existingLike = postLikeRepository.findByPostIdAndUserId(dto.getPostId(), dto.getUserId()).orElse(null);

        if (existingLike == null) {
            return ResponseEntity.badRequest().body("Post not liked yet.");
        }

        postLikeRepository.delete(existingLike);
        return ResponseEntity.ok("Post unliked.");
    }
}
