package com.modiconme.realworld.domain.userlogin;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Password;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
final class AuthorizedUser {

    private final ExistedByEmailUser existedByEmailUser;

    static Result<AuthorizedUser> emerge(ExistedByEmailUser user, Password enterredPassword,
                                         PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(enterredPassword, user.getEncodedPassword())) {
            return Result.failure(ApiException.notFound("Password does not match"));
        }

        return Result.success(new AuthorizedUser(user));
    }


}
