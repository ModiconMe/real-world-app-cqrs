package com.modiconme.realworld.domain.repository;

import com.modiconme.realworld.domain.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<UserEntity> findByEmailOrUsername(String email, String username);

    Optional<UserEntity> findById(long id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    UserEntity save(UserEntity user);

}
