package com.farhan.Socio.entity;

import java.time.LocalDate;

public interface UserFollowerStatsProjection {
    Long getUserId();
    String getUserName();
    Long getFollowerCount();
    LocalDate getFollowedDate();
}
