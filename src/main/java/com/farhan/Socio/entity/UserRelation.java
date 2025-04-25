package com.farhan.Socio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "user_relations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_user_id", nullable = false)
    private User sourceUser; // who follows/unfollows

    @ManyToOne
    @JoinColumn(name = "target_user_id", nullable = false)
    private User targetUser; // who is followed/unfollowed

    @Enumerated(EnumType.STRING)
    private RelationType relationType; // FOLLOW or UNFOLLOW

    @CreationTimestamp
    private LocalDateTime createdAt;

}