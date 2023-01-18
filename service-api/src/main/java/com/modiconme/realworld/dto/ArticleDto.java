package com.modiconme.realworld.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
public record ArticleDto(
        String slug,
        String title,
        String description,
        String body,
        List<String> tagList,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        Boolean favorited,
        Long favoritesCount,
        ProfileDto author
) { }
