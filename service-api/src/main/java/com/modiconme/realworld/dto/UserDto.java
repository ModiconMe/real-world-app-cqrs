package com.modiconme.realworld.dto;

import lombok.Builder;

@Builder
public record UserDto(
        String email,
        String token,
        String username,
        String bio,
        String image
) { }


