package com.modiconme.realworld.domain.loginuser;

import com.modiconme.realworld.domain.common.Email;
import com.modiconme.realworld.domain.common.Password;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.Username;
import com.modiconme.realworld.domain.registeruser.UnvalidatedRegisterUserRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class ValidatedLoginUserRequest {
    private final Email email;
    private final Password password;

    public static Result<ValidatedLoginUserRequest> emerge(UnvalidatedLoginUserRequest unvalidatedRequest) {
        return wrapPrimitivesToValueObjects(unvalidatedRequest)
                .map(ValidatedLoginUserRequest::tupleToValidatedLoginUserRequest);
    }

    private static ValidatedLoginUserRequest tupleToValidatedLoginUserRequest(Pair<Email, Password> tuple) {
        return new ValidatedLoginUserRequest(tuple.getValue0(), tuple.getValue1());
    }

    private static Result<Pair<Email, Password>> wrapPrimitivesToValueObjects(
            UnvalidatedLoginUserRequest unvalidatedRequest) {
        return Result.zip(
                Email.emerge(unvalidatedRequest.email()),
                Password.emerge(unvalidatedRequest.password())
        );
    }
}
