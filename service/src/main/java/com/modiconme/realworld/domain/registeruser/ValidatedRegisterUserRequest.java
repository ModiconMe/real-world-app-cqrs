package com.modiconme.realworld.domain.registeruser;

import com.modiconme.realworld.domain.common.Email;
import com.modiconme.realworld.domain.common.Password;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.Username;
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

    public static Result<ValidatedRegisterUserRequest> emerge(UnvalidatedRegisterUserRequest unvalidatedRequest) {
        return wrapPrimitivesToValueObjects(unvalidatedRequest)
                .map(ValidatedRegisterUserRequest::tuple4ToValidatedRegisterUserRequest);
    }

    private static ValidatedRegisterUserRequest tuple4ToValidatedRegisterUserRequest(Triplet<Email, Username, Password> tuple3) {
        return new ValidatedRegisterUserRequest(tuple3.getValue0(), tuple3.getValue1(), tuple3.getValue2());
    }

    private static Result<Triplet<Email, Username, Password>> wrapPrimitivesToValueObjects(
            UnvalidatedRegisterUserRequest unvalidatedRequest) {
        return Result.zip(
                Email.emerge(unvalidatedRequest.getEmail()),
                Username.emerge(unvalidatedRequest.getUsername()),
                Password.emerge(unvalidatedRequest.getPassword())
        );
    }
}
