package com.modiconme.realworld.domain.userregister;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.unprocessableEntity;

@RequiredArgsConstructor
@Repository
class RegisterUserGateway {

    private final RegisterUserRepository repository;

    private static UserEntity mapToUserEntity(ValidatedRegisterUserRequest request) {
        return UserEntity.register(
                request.getEmail().getValue(),
                request.getPassword().getValue(),
                request.getUsername().getValue());
    }

    Result<Boolean> existByEmail(String email) {
        return repository.existsByEmail(email)
                ? Result.failure(unprocessableEntity("User with email %s already exists", email))
                : Result.success(true);
    }

    Result<RegisteredUser> save(ValidatedRegisterUserRequest request) {
        UserEntity registeredUser = repository.save(mapToUserEntity(request));
        return RegisteredUser.emerge(registeredUser.getId(), request);
    }

}
