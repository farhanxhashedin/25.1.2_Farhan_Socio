package com.farhan.Socio.dto;

import lombok.Data;

@Data
public class FollowRequestDTO {
    private String sourceUserId;
    private String targetUserId;
}