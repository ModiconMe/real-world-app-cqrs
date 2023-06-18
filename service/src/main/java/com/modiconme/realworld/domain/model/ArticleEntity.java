package com.modiconme.realworld.domain.model;

import com.modiconme.realworld.domain.repository.ArticleRepository;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Set;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(
        name = "article",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_article_slug", columnNames = "slug")
        },
        indexes = {
                @Index(name = "slug_index", columnList = "slug"),
                @Index(name = "author_index", columnList = "id_author")
        }
)
public class ArticleEntity {

        @EqualsAndHashCode.Include
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(nullable = false)
        private String slug;
        @Column(nullable = false)
        private String title;
        @Column(nullable = false)
        private String description;

        @JdbcTypeCode(SqlTypes.LONGNVARCHAR)
        @Column(nullable = false)
        private String body;

        @Column(nullable = false)
        private ZonedDateTime createdAt;
        @Column(nullable = false)
        private ZonedDateTime updatedAt;

        @ManyToOne
        @JoinColumn(
                name = "id_author", referencedColumnName = "id", nullable = false,
                foreignKey = @ForeignKey(name = "article_id_account_id_fk")
        )
        private UserEntity author;

        @Singular
        @OneToMany(cascade = CascadeType.ALL)
        @JoinColumn(name = "id_article", referencedColumnName = "id", nullable = false,
                foreignKey = @ForeignKey(name = "article_id_comment_id_fk")
        )
        private Set<CommentEntity> comments;

        @Singular
        @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        @JoinTable(
                name = "article_article_tag",
                joinColumns = @JoinColumn(name = "id_article"),
                inverseJoinColumns = @JoinColumn(name = "id_tag")
        )
        private Set<TagEntity> tags;

        @Singular(value = "favoriteList")
        @ManyToMany
        @JoinTable(
                name = "article_user_favorite",
                joinColumns = @JoinColumn(name = "id_article"),
                inverseJoinColumns = @JoinColumn(name = "id_user")
        )
        private Set<UserEntity> favoriteList;

        public static ArticleEntity getExistedArticleOrThrow(String slug, ArticleRepository articleRepository) {
                return articleRepository.findBySlug(slug)
                        .orElseThrow(() -> exception(HttpStatus.NOT_FOUND,
                                "article with slug [%s] is not found", slug));
        }
}
