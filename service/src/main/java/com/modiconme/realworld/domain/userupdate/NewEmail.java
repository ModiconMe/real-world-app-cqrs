package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Email;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
final class NewEmail implements ValueObject<Optional<String>> {

    private static final String EMAIL_PATTERN = "^[a-zA-Z]\\w+@\\w+(\\.org|\\.com|\\.ru)$";
    private static final int EMAIL_MAX_LENGTH = 64;
    private final String value;

    static Result<NewEmail> emerge(String value) {
        if (value == null) {
            return Result.success(new NewEmail(null));
        }

        if (isInvalidEmail(value)) {
            return Result.failure(new IllegalArgumentException("Invalid email: '%s'".formatted(value)));
        }

        return Result.success(new NewEmail(value));
    }

    private static boolean isInvalidEmail(String email) {
        return email == null || !email.matches(EMAIL_PATTERN) || email.length() > EMAIL_MAX_LENGTH;
    }

    Email mapToEmail(Email oldEmail) {
        return getValue()
                .map(Email::emerge)
                .orElse(Result.success(oldEmail))
                .getData();
    }


    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }
}
