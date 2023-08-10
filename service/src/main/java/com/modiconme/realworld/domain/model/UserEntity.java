package com.modiconme.realworld.domain.model;

import com.modiconme.realworld.domain.repository.ArticleRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Set;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.notFound;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"followers"})
@Getter
@Setter
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_user_email", columnNames = "email"),
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public static UserEntity getExistedUserOrThrow(String username, UserRepository userRepository) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> notFound("Profile not found", username));
    }

    public CommentEntity writeComment(String body, ArticleEntity article, ArticleRepository articleRepository) {
        CommentEntity comment = CommentEntity.builder()
                .body(body)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .author(this)
                .build();

        articleRepository.save(
                article.toBuilder()
                        .comment(comment)
                        .build()
        );
        return comment;
    }
}


