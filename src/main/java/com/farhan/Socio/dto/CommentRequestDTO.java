package com.farhan.Socio.dto;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private Long postId;
    private String userId;
    private String content;
}