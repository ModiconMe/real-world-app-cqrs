package com.modiconme.realworld.infrastructure.security;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Email;
import com.modiconme.realworld.domain.common.valueobjects.EncodedPassword;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.javatuples.Quartet;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
final class ExistedByUsernameUser {

    private final UserId userId;
    private final Username username;
    private final Email email;
    private final EncodedPassword encodedPassword;

    static Result<ExistedByUsernameUser> emerge(long userId, String username, String email, String hash) {
        return wrapPrimitivesToValueObjects(userId, username, email, hash)
                .map(ExistedByUsernameUser::mapTupleToDomain);
    }

    private static Result<Quartet<UserId, Username, Email, EncodedPassword>> wrapPrimitivesToValueObjects(
            long userId, String username, String email, String hash
    ) {
        return Result.zip(
                UserId.emerge(userId),
                Username.emerge(username),
                Email.emerge(email),
                EncodedPassword.fromStringHash(hash)
        );
    }

    private static ExistedByUsernameUser mapTupleToDomain(
            Quartet<UserId, Username, Email, EncodedPassword> it
    ) {
        return new ExistedByUsernameUser(
                it.getValue0(),
                it.getValue1(),
                it.getValue2(),
                it.getValue3()
        );
    }

}
