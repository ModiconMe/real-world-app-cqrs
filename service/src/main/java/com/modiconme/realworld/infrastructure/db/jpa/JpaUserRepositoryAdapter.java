package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.domain.registeruser.UserRepository;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.unprocessableEntity;

@RequiredArgsConstructor
@Repository
public class JpaUserRepositoryAdapter implements UserRepository {

    private final DataUserRepository repository;

    @Override
    public Result<Boolean> existByEmail(String email) {
        boolean existsByEmail = repository.existsByEmail(email);
        return existsByEmail
                ? Result.failure(unprocessableEntity("User with email %s already exists", email))
                : Result.success(true);
    }

    @Override
    public Result<UserEntity> save(UserEntity user) {
        UserEntity saved = repository.save(user);
        return Result.success(saved);
    }
}
