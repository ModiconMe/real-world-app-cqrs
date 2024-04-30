package com.modiconme.realworld.infrastructure.repository.jpa;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.domain.common.UserRepository;
import com.modiconme.realworld.dto.ProfileDto;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.notFound;
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

    @Override
    public Result<UserEntity> findByEmail(String email) {
        Optional<UserEntity> user = repository.findByEmail(email);
        return user.map(Result::success)
                .orElseGet(() -> Result.failure(notFound("User with email %s not exists", email)));
    }

    @Override
    public Result<UserEntity> findByUsername(String username) {
        Optional<UserEntity> user = repository.findByUsername(username);
        return user.map(Result::success)
                .orElseGet(() -> Result.failure(notFound("User with username %s not exists", username)));
    }

    private static ProfileDto mapTupleToProfileDto(Tuple it) {
        return new ProfileDto(
                it.get("username", String.class),
                it.get("bio", String.class),
                it.get("image", String.class),
                it.get("following", Boolean.class)
        );
    }

    @Override
    public Result<ProfileDto> findProfile(String profileUsername, String currentUsername) {
        Optional<ProfileDto> profile = repository.findProfile(currentUsername, profileUsername)
                .map(JpaUserRepositoryAdapter::mapTupleToProfileDto);
        return profile.map(Result::success)
                .orElseGet(() -> Result.failure(notFound("Profile with username %s not exists", profileUsername)));
    }
}
