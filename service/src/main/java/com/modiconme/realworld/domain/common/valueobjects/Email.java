package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import io.vavr.control.Try;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public final class Email implements ValueObject<String> {

    private static final String EMAIL_PATTERN = "^[a-zA-Z]\\w+@\\w+(\\.org|\\.com|\\.ru)$";
    private static final int EMAIL_MAX_LENGTH = 64;
    private final String value;

    public static Result<Email> emerge(String value) {
        if (isInvalidEmail(value)) {
            return Result.failure(new IllegalArgumentException("Invalid email: '%s'".formatted(value)));
        }

        return Result.success(new Email(value));
    }

    public static Try<Email> emergeTry(String value) {
        /*
        return API.Match(isInvalidEmail(value)).of(
                Case($(true), () -> Try.failure(new IllegalArgumentException("Invalid email: '%s'".formatted(value)))),
                Case($(false), () -> Try.success(new Email(value)))
        );
        */


        if (isInvalidEmail(value)) {
            return Try.failure(new IllegalArgumentException("Invalid email: '%s'".formatted(value)));
        }

        return Try.success(new Email(value));
    }

    private static boolean isInvalidEmail(String email) {
        return email == null || !email.matches(EMAIL_PATTERN) || email.length() > EMAIL_MAX_LENGTH;
    }
}
