package com.modiconme.realworld.domain.common;

public interface UserRepository {

    Result<Boolean> existByEmail(String email);
    Result<UserEntity> save(UserEntity user);
    Result<UserEntity> findByEmail(String email);

    Result<UserEntity> findByUsername(String username);

}
