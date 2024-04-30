package com.modiconme.realworld.dto;

import lombok.Builder;

@Builder
public record ProfileDto(
        String username,
        String bio,
        String image,
        Boolean following) {
}


