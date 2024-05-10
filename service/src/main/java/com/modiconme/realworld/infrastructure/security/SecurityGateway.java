package com.modiconme.realworld.infrastructure.security;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.notFound;

@RequiredArgsConstructor
@Repository
class SecurityGateway {

    private final SecutiryRepository secutiryRepository;

    private static Result<ExistedByUsernameUser> mapExistedByEmailUser(Tuple it) {
        return ExistedByUsernameUser.emerge(
                it.get("id", Long.class),
                it.get("username", String.class),
                it.get("email", String.class),
                it.get("password", String.class)
        );
    }

    Result<ExistedByUsernameUser> findByUsername(Username username) {
        return secutiryRepository.findByUsername(username.getValue())
                .map(SecurityGateway::mapExistedByEmailUser)
                .orElseGet(() -> Result.failure(notFound("User with username %s not exists",
                        username.getValue())));
    }
}
