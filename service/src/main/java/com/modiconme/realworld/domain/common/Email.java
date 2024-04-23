package com.modiconme.realworld.domain.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = PRIVATE)
public final class Email implements ValueObject<String> {

    private final String value;

    public static Result<Email> emerge(String value) {
        if (isInvalidEmail(value)) {
           return Result.failure(new IllegalArgumentException("Invalid email: '%s'".formatted(value)));
        }

        return Result.success(new Email(value));
    }

    private static final String EMAIL_PATTERN = "^[a-zA-Z]\\w+@\\w+(\\.org|\\.com|\\.ru)$";
    private static final int EMAIL_MAX_LENGTH = 64;

    private static boolean isInvalidEmail(String email) {
        return email == null || !email.matches(EMAIL_PATTERN) || email.length() > EMAIL_MAX_LENGTH;
    }
}
