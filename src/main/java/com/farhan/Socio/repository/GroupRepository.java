package com.farhan.Socio.repository;

import com.farhan.Socio.entity.Group;
import com.farhan.Socio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByNameContainingIgnoreCase(String name);

    @Query("SELECT g FROM Group g WHERE g.createdBy.id = :userId OR :user MEMBER OF g.members")
    List<Group> findAllByUserInvolved(@Param("userId") Long userId, @Param("user") User user);
}
