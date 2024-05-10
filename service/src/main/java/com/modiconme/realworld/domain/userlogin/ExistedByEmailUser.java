package com.modiconme.realworld.domain.userlogin;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.javatuples.Sextet;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
final class ExistedByEmailUser {

    private final UserId userId;
    private final Username username;
    private final Email email;
    private final EncodedPassword encodedPassword;
    private final Bio bio;
    private final Image image;

    static Result<ExistedByEmailUser> emerge(long userId, String username, String email, String hash,
                                             String bio, String image) {
        return wrapPrimitivesToValueObjects(userId, username, email, hash, bio, image)
                .map(ExistedByEmailUser::mapTupleToDomain);
    }

    private static Result<Sextet<UserId, Username, Email, EncodedPassword, Bio, Image>> wrapPrimitivesToValueObjects(
            long userId, String username, String email, String hash, String bio, String image
    ) {
        return Result.zip(
                UserId.emerge(userId),
                Username.emerge(username),
                Email.emerge(email),
                EncodedPassword.fromStringHash(hash),
                Bio.emerge(bio),
                Image.emerge(image)
        );
    }

    private static ExistedByEmailUser mapTupleToDomain(
            Sextet<UserId, Username, Email, EncodedPassword, Bio, Image> it
    ) {
        return new ExistedByEmailUser(
                it.getValue0(),
                it.getValue1(),
                it.getValue2(),
                it.getValue3(),
                it.getValue4(),
                it.getValue5()
        );
    }

}
