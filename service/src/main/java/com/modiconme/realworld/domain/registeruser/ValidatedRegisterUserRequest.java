package com.modiconme.realworld.domain.registeruser;

import com.modiconme.realworld.domain.common.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Triplet;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class ValidatedRegisterUserRequest {
    private final Email email;
    private final Username username;
    private final Password password;

    public static Result<ValidatedRegisterUserRequest> emerge(UnvalidatedRegisterUserRequest unvalidatedRequest,
                                                              PasswordEncoder passwordEncoder) {
        return wrapPrimitivesToValueObjects(unvalidatedRequest)
                .map(ValidatedRegisterUserRequest::tupleToValidatedRegisterUserRequest);
    }

    private static ValidatedRegisterUserRequest tupleToValidatedRegisterUserRequest(Triplet<Email, Username, Password> tuple) {
        return new ValidatedRegisterUserRequest(tuple.getValue0(), tuple.getValue1(), tuple.getValue2());
    }

    private static Result<Triplet<Email, Username, Password>> wrapPrimitivesToValueObjects(
            UnvalidatedRegisterUserRequest unvalidatedRequest) {
        return Result.zip(
                Email.emerge(unvalidatedRequest.email()),
                Username.emerge(unvalidatedRequest.username()),
                Password.emerge(unvalidatedRequest.password())
        );
    }
}
