package com.modiconme.realworld.domain.articlecreate;

public record AuthorDto(
        String username,
        String bio,
        String image,
        Boolean following
) {

    AuthorDto(String username, String bio, String image) {
        this(username, bio, image, false);
    }
}


