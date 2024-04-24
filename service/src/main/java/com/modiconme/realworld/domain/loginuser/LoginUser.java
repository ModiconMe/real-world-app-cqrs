package com.modiconme.realworld.domain.loginuser;

import com.modiconme.realworld.domain.common.Password;
import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class LoginUser {

    private final UserEntity userEntity;

    public static Result<LoginUser> emerge(UserEntity userEntity, Password password, PasswordEncoder passwordEncoder) {
        if (userEntity == null) {
            return Result.failure(ApiException.notFound("User not found"));
        }

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            return Result.failure(ApiException.notFound("Password does not match"));
        }

        return Result.success(new LoginUser(userEntity));
    }
}
