package com.modiconme.realworld.domain.articlecreate;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public record ArticleDto(
        String slug,
        String title,
        String description,
        String body,
        List<String> tagList,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        boolean favorited,
        long favoritesCount,
        AuthorDto author
) {

    ArticleDto(
            Title title,
            Slug slug,
            Description description,
            Body body,
            TagList tagList,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt,
            AuthorDto author
    ) {
        this(slug.getValue(), title.getValue(), description.getValue(), body.getValue(), tagList.getValue(),
                createdAt, updatedAt, false, 0, author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleDto that = (ArticleDto) o;
        return favorited == that.favorited && favoritesCount == that.favoritesCount && Objects.equals(slug, that.slug) && Objects.equals(body, that.body) && Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(description, that.description) && Objects.equals(tagList, that.tagList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, title, description, body, tagList, favorited, favoritesCount, author);
    }
}

