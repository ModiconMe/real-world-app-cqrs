package com.modiconme.realworld.domain.repository;

import com.modiconme.realworld.domain.model.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    UserEntity save(UserEntity user);

}
