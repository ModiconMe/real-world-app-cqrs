package com.modiconme.realworld.domain.common;

import com.modiconme.realworld.dto.ProfileDto;

public interface UserRepository {

    Result<Boolean> existByEmail(String email);

    Result<UserEntity> save(UserEntity user);

    Result<UserEntity> findByEmail(String email);

    Result<UserEntity> findByUsername(String username);

    Result<ProfileDto> findProfile(String profileUsername, String currentUsername);

}
