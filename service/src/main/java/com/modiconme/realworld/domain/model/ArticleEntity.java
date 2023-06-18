package com.modiconme.realworld.domain.model;

import com.modiconme.realworld.domain.repository.ArticleRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "article_slug_unique", columnNames = "slug")
        },
        indexes = {
                @Index(name = "slug_index", columnList = "slug"),
                @Index(name = "author_index", columnList = "author_id")
        }
)
public class ArticleEntity {

        @Id
        private UUID id;

        @Column(nullable = false)
        private String slug;
        @Column(nullable = false)
        private String title;
        @Column(nullable = false)
        private String description;

        @Lob
        @Column(nullable = false)
        private String body;

        @Column(nullable = false)
        private ZonedDateTime createdAt;
        @Column(nullable = false)
        private ZonedDateTime updatedAt;

        @ManyToOne
        @JoinColumn(
                name = "author_id", referencedColumnName = "id", nullable = false,
                foreignKey = @ForeignKey(name = "article_id_account_id_fk")
        )
        private UserEntity author;

        @Singular
        @OneToMany(cascade = CascadeType.ALL)
        @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false,
                foreignKey = @ForeignKey(name = "article_id_comment_id_fk")
        )
        private Set<CommentEntity> comments;

        @Singular
        @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinTable(
                name = "article_tags",
                joinColumns = @JoinColumn(name = "article_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id")
        )
        private Set<TagEntity> tags;

        @Singular(value = "favoriteList")
        @ManyToMany
        @JoinTable(
                name = "favorite_by",
                joinColumns = @JoinColumn(name = "article_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id")
        )
        private Set<UserEntity> favoriteList;

        public static ArticleEntity getExistedArticleOrThrow(String slug, ArticleRepository articleRepository) {
                return articleRepository.findBySlug(slug)
                        .orElseThrow(() -> exception(HttpStatus.NOT_FOUND,
                                "article with slug [%s] is not found", slug));
        }
}
