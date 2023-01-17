package com.modiconme.realworld.dto;

import java.time.ZonedDateTime;

public record CommentDto(
        Long id,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        String body,
        ProfileDto author
) { }
