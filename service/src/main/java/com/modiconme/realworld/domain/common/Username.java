package com.modiconme.realworld.domain.common;

import liquibase.util.StringUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class Username implements ValueObject<String> {

    private final String value;

    public static Result<Username> emerge(String value) {
        if (isInvalidUsername(value)) {
            return Result.failure(new IllegalArgumentException("Invalid username: '%s'".formatted(value)));
        }

        return Result.success(new Username(value));
    }

    private static final int USERNAME_MAX_LENGTH = 64;

    private static boolean isInvalidUsername(String value) {
        return !StringUtils.hasText(value) || value.length() > USERNAME_MAX_LENGTH;
    }
}
