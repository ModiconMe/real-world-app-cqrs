package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public final class Bio implements ValueObject<Optional<String>> {

    private final String value;

    public static Result<Bio> emerge(@Nullable String value) {
        if (value != null && value.length() > 256) {
            return Result.failure(ApiException.notFound("Invalid bio value"));
        }

        return Result.success(new Bio(value));
    }

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }
}
