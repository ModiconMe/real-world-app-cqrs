package com.modiconme.realworld.domain.userregister;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Email;
import com.modiconme.realworld.domain.common.valueobjects.EncodedPassword;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
final class RegisteredUser {

    private final UserId userId;
    private final Username username;
    private final Email email;
    private final EncodedPassword encodedPassword;

    static Result<RegisteredUser> emerge(
            long userId, ValidatedRegisterUserRequest request
    ) {
        return UserId.emerge(userId)
                .map(it -> new RegisteredUser(it, request.getUsername(), request.getEmail(), request.getPassword()));
    }
}
