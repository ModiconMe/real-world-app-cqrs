package com.modiconme.realworld.domain.model;

import com.modiconme.realworld.command.UpdateUser;
import com.modiconme.realworld.domain.repository.ArticleRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.Set;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.notFound;
import static org.springframework.util.StringUtils.hasText;

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

    public UserEntity updateUser(UpdateUser newUser, PasswordEncoder passwordEncoder) {
        return this.toBuilder()
                .username(getNewFieldIfValid(username, newUser.getUsername()))
                .email(getNewFieldIfValid(email, newUser.getEmail()))
                .password(getNewFieldIfValid(password, newUser.getPassword(), passwordEncoder))
                .bio(getNewFieldIfValid(bio, newUser.getBio()))
                .image(getNewFieldIfValid(bio, newUser.getImage()))
                .updatedAt(ZonedDateTime.now())
                .build();
    }

    private String getNewFieldIfValid(String oldField, String newField) {
        return hasText(newField) && !newField.equals(oldField) ? newField : oldField;
    }

    private String getNewFieldIfValid(String oldField, String newField, PasswordEncoder passwordEncoder) {
        return hasText(newField) && !passwordEncoder.matches(newField, oldField) ? passwordEncoder.encode(newField) : oldField;
    }

    public FollowRelationEntity followUser(UserEntity followee) {
        FollowRelationEntity followRelation = new FollowRelationEntity(
                new FollowRelationId(followee.getId(), id),
                followee,
                this
        );
        followee.toBuilder().follower(followRelation).build();
        return followRelation;
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


