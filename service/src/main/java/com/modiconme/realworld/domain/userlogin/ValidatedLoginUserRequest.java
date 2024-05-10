package com.modiconme.realworld.domain.userlogin;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Email;
import com.modiconme.realworld.domain.common.valueobjects.Password;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class ValidatedLoginUserRequest {
    private final Email email;
    private final Password password;

    public static Result<ValidatedLoginUserRequest> emerge(LoginUserRequest unvalidatedRequest) {
        return wrapPrimitivesToValueObjects(unvalidatedRequest)
                .map(ValidatedLoginUserRequest::tupleToValidatedLoginUserRequest);
    }

    private static ValidatedLoginUserRequest tupleToValidatedLoginUserRequest(Pair<Email, Password> tuple) {
        return new ValidatedLoginUserRequest(tuple.getValue0(), tuple.getValue1());
    }

    private static Result<Pair<Email, Password>> wrapPrimitivesToValueObjects(
            LoginUserRequest unvalidatedRequest) {
        return Result.zip(
                Email.emerge(unvalidatedRequest.email()),
                Password.emerge(unvalidatedRequest.password())
        );
    }
}
