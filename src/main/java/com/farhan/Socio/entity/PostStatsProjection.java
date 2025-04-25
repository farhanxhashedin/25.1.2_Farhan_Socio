package com.farhan.Socio.entity;

import java.time.LocalDate;

public interface PostStatsProjection {
    Long getPostId();
    String getUserName();
    LocalDate getPostDate();
    FileType getFileType();
    Long getLikeCount();
    Long getCommentCount();
    Boolean getIsGroupLinked();
}
