package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.EncodedPassword;
import com.modiconme.realworld.domain.common.valueobjects.Password;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
final class NewPassword implements ValueObject<Optional<String>> {

    private static final int PASSWORD_MAX_LENGTH = 64;
    private final String value;

    static Result<NewPassword> emerge(String value) {
        if (value == null) {
            return Result.success(new NewPassword(null));
        }

        if (isInvalid(value)) {
            return Result.failure(ApiException.unprocessableEntity("Invalid password: '%s'".formatted(value)));
        }

        return Result.success(new NewPassword(value));
    }

    private static boolean isInvalid(String value) {
        return !StringUtils.hasText(value) || value.length() > PASSWORD_MAX_LENGTH;
    }

    EncodedPassword mapToEncodedPassword(
            PasswordEncoder passwordEncoder, EncodedPassword oldPassword
    ) {
        return getValue()
                .map(it -> Password.emerge(it)
                        .flatMap(password -> password.encode(passwordEncoder)))
                .orElse(Result.success(oldPassword))
                .getData();
    }

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }
}
