package com.modiconme.realworld.domain.userregister;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Email;
import com.modiconme.realworld.domain.common.valueobjects.EncodedPassword;
import com.modiconme.realworld.domain.common.valueobjects.Password;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Triplet;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class ValidatedRegisterUserRequest {
    private final Email email;
    private final Username username;
    private final EncodedPassword password;

    static Result<ValidatedRegisterUserRequest> emerge(RegisterUserRequest unvalidatedRequest,
                                                              PasswordEncoder passwordEncoder) {
        return wrapPrimitivesToValueObjects(unvalidatedRequest, passwordEncoder)
                .map(ValidatedRegisterUserRequest::tupleToValidatedRegisterUserRequest);
    }

    private static ValidatedRegisterUserRequest tupleToValidatedRegisterUserRequest(
            Triplet<Email, Username, EncodedPassword> tuple
    ) {
        return new ValidatedRegisterUserRequest(
                tuple.getValue0(),
                tuple.getValue1(),
                tuple.getValue2()
        );
    }

    private static Result<Triplet<Email, Username, EncodedPassword>> wrapPrimitivesToValueObjects(
            RegisterUserRequest unvalidatedRequest, PasswordEncoder passwordEncoder
    ) {
        return Result.zip(
                Email.emerge(unvalidatedRequest.email()),
                Username.emerge(unvalidatedRequest.username()),
                Password.emerge(unvalidatedRequest.password())
                        .flatMap(it -> it.encode(passwordEncoder))
        );
    }
}
