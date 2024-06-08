package com.modiconme.realworld.domain.articlecreate;

import java.time.Instant;
import java.util.List;

public record ArticleDto(
        String slug,
        String title,
        String description,
        String body,
        List<String> tagList,
        Instant createdAt,
        Instant updatedAt,
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
            Instant createdAt,
            Instant updatedAt,
            AuthorDto author
    ) {
        this(slug.getValue(), title.getValue(), description.getValue(), body.getValue(), tagList.getValue(),
                createdAt, updatedAt, false, 0, author);
    }
}

