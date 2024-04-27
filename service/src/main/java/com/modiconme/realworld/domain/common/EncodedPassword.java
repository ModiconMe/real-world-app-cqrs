package com.modiconme.realworld.domain.common;

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
}
