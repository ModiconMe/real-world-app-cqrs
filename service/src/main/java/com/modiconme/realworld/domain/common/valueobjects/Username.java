package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public final class Username implements ValueObject<String> {

    private final String value;

    public static Result<Username> emerge(String value) {
        if (isInvalidUsername(value)) {
            return Result.failure(ApiException.unprocessableEntity("Invalid username: '%s'".formatted(value)));
        }

        return Result.success(new Username(value));
    }

    private static final int USERNAME_MAX_LENGTH = 64;

    private static boolean isInvalidUsername(String value) {
        return !StringUtils.hasText(value) || value.length() > USERNAME_MAX_LENGTH;
    }
}
