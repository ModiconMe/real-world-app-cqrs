package com.modiconme.realworld.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class Password implements ValueObject<String> {

    private final String value;

    public static Result<Password> emerge(String value) {
        if (isInvalidUsername(value)) {
            return Result.failure(new IllegalArgumentException("Invalid password: '%s'".formatted(value)));
        }

        return Result.success(new Password(value));
    }

    private static final int PASSWORD_MAX_LENGTH = 128;

    private static boolean isInvalidUsername(String value) {
        return !StringUtils.hasText(value) || value.length() > PASSWORD_MAX_LENGTH;
    }
}
