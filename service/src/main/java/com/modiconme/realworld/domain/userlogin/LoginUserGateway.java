package com.modiconme.realworld.domain.userlogin;

import com.modiconme.realworld.domain.common.Result;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.notFound;

@Repository
@RequiredArgsConstructor
class LoginUserGateway {

    private final LoginUserRepository repository;

    private static Result<ExistedByEmailUser> mapExistedByEmailUser(Tuple it) {
        return ExistedByEmailUser.emerge(
                it.get("id", Long.class),
                it.get("username", String.class),
                it.get("email", String.class),
                it.get("password", String.class),
                it.get("bio", String.class),
                it.get("image", String.class)
        );
    }

    Result<ExistedByEmailUser> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(LoginUserGateway::mapExistedByEmailUser)
                .orElseGet(() -> Result.failure(notFound("User with email %s not exists", email)));
    }
}
