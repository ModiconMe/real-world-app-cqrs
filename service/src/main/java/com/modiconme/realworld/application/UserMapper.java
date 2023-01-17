package com.modiconme.realworld.application;

import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.UserDto;

public class UserMapper {

    public static UserDto mapToDto(UserEntity user, String token) {
        return UserDto.builder()
                .email(user.getEmail())
                .token(token)
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }

}
