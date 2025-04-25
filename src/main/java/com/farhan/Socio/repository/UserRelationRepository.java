package com.farhan.Socio.repository;

import com.farhan.Socio.entity.UserFollowerStatsProjection;
import com.farhan.Socio.entity.UserRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRelationRepository extends JpaRepository<UserRelation, Long> {
    Optional<UserRelation> findBySourceUserIdAndTargetUserId(String sourceUserId, String targetUserId);
    @Query("SELECT ur.targetUser.id AS userId, ur.targetUser.name AS userName, COUNT(ur.sourceUser.id) AS followerCount, DATE(ur.createdAt) AS followedDate " +
            "FROM UserRelation ur " +
            "WHERE ur.relationType = 'FOLLOW' " +
            "GROUP BY ur.targetUser.id, ur.targetUser.name, DATE(ur.createdAt) " +
            "ORDER BY DATE(ur.createdAt), followerCount DESC")
    List<UserFollowerStatsProjection> getUsersGroupedByDateOrderedByFollowers();

}