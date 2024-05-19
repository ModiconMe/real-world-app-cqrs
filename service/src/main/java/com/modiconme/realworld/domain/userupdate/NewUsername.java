package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
final class NewUsername implements ValueObject<Optional<String>> {

    private static final int USERNAME_MAX_LENGTH = 64;
    private final String value;

    static Result<NewUsername> emerge(String value) {
        if (value == null) {
            return Result.success(new NewUsername(null));
        }

        if (isInvalidUsername(value)) {
            return Result.failure(ApiException.unprocessableEntity("Invalid username: '%s'".formatted(value)));
        }

        return Result.success(new NewUsername(value));
    }

    private static boolean isInvalidUsername(String value) {
        return !StringUtils.hasText(value) || value.length() > USERNAME_MAX_LENGTH;
    }

    Username mapToUsername(Username oldUsername) {
        return getValue()
                .map(Username::emerge)
                .orElse(Result.success(oldUsername))
                .getData();
    }

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }
}
