package com.farhan.Socio.repository;

import com.farhan.Socio.entity.Post;
import com.farhan.Socio.entity.PostStatsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
    SELECT 
        p.id as postId,
        p.user.name as userName,
        DATE(p.createdAt) as postDate,
        p.fileType as fileType,
        COUNT(DISTINCT pl.id) as likeCount,
        COUNT(DISTINCT c.id) as commentCount,
        CASE WHEN p.group IS NOT NULL THEN true ELSE false END as isGroupLinked
    FROM Post p
    LEFT JOIN PostLike pl ON pl.post.id = p.id
    LEFT JOIN Comment c ON c.post.id = p.id
    GROUP BY p.id, p.user.name, DATE(p.createdAt), p.fileType, p.group
    ORDER BY likeCount DESC, commentCount DESC
""")
    List<PostStatsProjection> getAllPostStats();

}
