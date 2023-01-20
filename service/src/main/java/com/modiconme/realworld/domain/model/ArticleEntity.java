package com.modiconme.realworld.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

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
}
