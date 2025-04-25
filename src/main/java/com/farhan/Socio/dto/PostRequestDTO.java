package com.farhan.Socio.dto;

import com.farhan.Socio.entity.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {
    private String userId;
    private String content;
    private String fileUrl;
    private FileType fileType;
    private Long sharedFromPostId;
}