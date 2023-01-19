package com.modiconme.realworld.domain.repository;

import com.modiconme.realworld.domain.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    UserEntity save(UserEntity user);

}
