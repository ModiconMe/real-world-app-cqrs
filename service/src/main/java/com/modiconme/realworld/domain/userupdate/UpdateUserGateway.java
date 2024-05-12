package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import io.vavr.control.Try;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.notFound;
import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.unprocessableEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
class UpdateUserGateway {

    private final UpdateUserRepository repository;

    private static Result<ExistedByIdUser> mapExistedByIdUser(Tuple it) {
        return ExistedByIdUser.emerge(
                it.get("id", Long.class),
                it.get("username", String.class),
                it.get("email", String.class),
                it.get("password", String.class),
                it.get("bio", String.class),
                it.get("image", String.class)
        );
    }

    Result<ExistedByIdUser> findUserToUpdate(UserId userId) {
        return repository.nativeFindById(userId.getValue())
                .map(UpdateUserGateway::mapExistedByIdUser)
                .orElseGet(() -> Result.failure(notFound("User with id %s not exists", userId.getValue())));
    }

    Result<PreparedForUpdateUser> checkEmailAndUsernameUnique(PreparedForUpdateUser newUser) {
        Tuple res = repository.checkUsernameAndEmailAvailable(
                newUser.getUserId().getValue(),
                newUser.getEmail().getValue(),
                newUser.getUsername().getValue()
        );

        if (Boolean.TRUE.equals(res.get("username_exists", Boolean.class))) {
            return Result.failure(unprocessableEntity("Username already exists"));
        }

        if (Boolean.TRUE.equals(res.get("email_exists", Boolean.class))) {
            return Result.failure(unprocessableEntity("Email already exists"));
        }

        return Result.success(newUser);
    }

    Result<PreparedForUpdateUser> updateUser(PreparedForUpdateUser newUser) {
        Try<Integer> tryUpdate = Try.of(() -> repository.updateUser(
                newUser.getUserId().getValue(),
                newUser.getEmail().getValue(),
                newUser.getUsername().getValue(),
                newUser.getPassword().getValue(),
                newUser.getBio().getValue().orElse(null),
                newUser.getImage().getValue().orElse(null)
        ));

        if (tryUpdate.isFailure()) {
            log.error("Update user {} failed with error: ", newUser.getUserId(), tryUpdate.getCause());
            return Result.failure(unprocessableEntity("Error occurred while updating user"));
        }

        return tryUpdate.get() > 0
                ? Result.success(newUser)
                : Result.failure(unprocessableEntity("Oops! User was not updated"));
    }
}
