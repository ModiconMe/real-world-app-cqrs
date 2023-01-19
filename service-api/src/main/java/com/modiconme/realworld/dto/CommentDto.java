package com.modiconme.realworld.dto;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record CommentDto(
        Long id,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        String body,
        ProfileDto author
) { }
