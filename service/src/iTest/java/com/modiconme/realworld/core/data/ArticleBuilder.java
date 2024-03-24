package com.modiconme.realworld.core.data;

import com.modiconme.realworld.core.data.var2.GenericBuilder;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.CommentEntity;
import com.modiconme.realworld.domain.model.TagEntity;
import com.modiconme.realworld.domain.model.UserEntity;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.modiconme.realworld.core.data.var2.GenericBuilder.uniqString;

public class ArticleBuilder<T extends ArticleEntity> implements GenericBuilder<T, ArticleBuilder<T>> {

    private String slug = uniqString();
    private String title = uniqString();
    private String description = uniqString();
    private String body = uniqString();
    private ZonedDateTime createdAt = ZonedDateTime.now();
    private ZonedDateTime updatedAt = ZonedDateTime.now();
    private UserEntity author;
    private Set<CommentEntity> comments = new HashSet<>();
    private Set<TagEntity> tags = new HashSet<>();
    private Set<UserEntity> favoriteList = new HashSet<>();

    public ArticleBuilder(UserEntity author) {
        this.author = author;
    }

    public ArticleBuilder<T> slug(String slug) {
        this.slug = slug;
        return this;
    }

    public ArticleBuilder<T> title(String title) {
        this.title = title;
        return this;
    }

    public ArticleBuilder<T> description(String description) {
        this.description = description;
        return this;
    }

    public ArticleBuilder<T> body(String body) {
        this.body = body;
        return this;
    }

    public ArticleBuilder<T> createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ArticleBuilder<T> updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public ArticleBuilder<T> author(UserEntity author) {
        this.author = author;
        return this;
    }

    public ArticleBuilder<T> comment(CommentEntity comment) {
        this.comments.add(comment);
        return this;
    }

    public ArticleBuilder<T> tag(TagEntity tag) {
        this.tags.add(tag);
        return this;
    }

    public ArticleBuilder<T> favoriteBy(UserEntity user) {
        this.favoriteList.add(user);
        return this;
    }

    @Override
    public T build() {
        return (T) ArticleEntity.builder()
                .slug(slug)
                .title(title)
                .description(description)
                .body(body)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .author(author)
                .comments(comments)
                .tags(tags)
                .favoriteList(favoriteList)
                .build();
    }
}
