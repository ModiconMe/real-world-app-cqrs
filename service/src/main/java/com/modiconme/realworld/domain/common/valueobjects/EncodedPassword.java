package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class EncodedPassword implements ValueObject<String> {

    private final String value;

    public static Result<EncodedPassword> emerge(Password password, PasswordEncoder passwordEncoder) {
        return Result.success(new EncodedPassword(passwordEncoder.encode(password)));
    }

    public static Result<EncodedPassword> fromStringHash(String hash) {
        return Result.success(new EncodedPassword(hash));
    }
}
