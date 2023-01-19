package com.modiconme.realworld.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"followers"})
@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "user_username_unique", columnNames = "username")
        },
        indexes = {
                @Index(name = "email_index", columnList = "email"),
                @Index(name = "username_index", columnList = "username")
        }
)
public class UserEntity {

    @EqualsAndHashCode.Include
    @Id
    private UUID id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;

    private String bio;
    private String image;

    @Column(nullable = false)
    private ZonedDateTime createdAt;
    @Column(nullable = false)
    private ZonedDateTime updatedAt;

    @Singular
    @OneToMany(
            mappedBy = "followee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<FollowRelationEntity> followers;
}


