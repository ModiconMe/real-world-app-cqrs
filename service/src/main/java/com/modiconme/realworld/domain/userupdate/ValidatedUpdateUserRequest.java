package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Bio;
import com.modiconme.realworld.domain.common.valueobjects.Image;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Sextet;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class ValidatedUpdateUserRequest {
    private final UserId userId;
    private final NewEmail email;
    private final NewUsername username;
    private final NewPassword password;
    private final Image image;
    private final Bio bio;

    static Result<ValidatedUpdateUserRequest> emerge(long userId, UpdateUserRequest request) {
        return wrapPrimitivesToValueObjects(userId, request)
                .map(ValidatedUpdateUserRequest::tupleToValidatedRequest);
    }

    private static Result<Sextet<UserId, NewEmail, NewUsername, NewPassword, Image, Bio>> wrapPrimitivesToValueObjects(
            long userId, UpdateUserRequest request
    ) {
        return Result.zip(
                UserId.emerge(userId),
                NewEmail.emerge(request.email()),
                NewUsername.emerge(request.username()),
                NewPassword.emerge(request.password()),
                Image.emerge(request.image()),
                Bio.emerge(request.bio())
        );
    }

    private static ValidatedUpdateUserRequest tupleToValidatedRequest(
            Sextet<UserId, NewEmail, NewUsername, NewPassword, Image, Bio> tuple
    ) {
        return new ValidatedUpdateUserRequest(
                tuple.getValue0(),
                tuple.getValue1(),
                tuple.getValue2(),
                tuple.getValue3(),
                tuple.getValue4(),
                tuple.getValue5()
        );
    }
}
