package com.modiconme.realworld.dto;

public record ProfileDto(
        String username,
        String bio,
        String image,
        Boolean following
) { }


