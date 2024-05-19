package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class Description implements ValueObject<String> {

    private final String value;

    static Result<Description> emerge(String value) {
        if (isValid(value)) {
            return Result.failure(ApiException.unprocessableEntity("Invalid description: '%s'".formatted(value)));
        }

        return Result.success(new Description(value));
    }

    private static boolean isValid(String value) {
        return !StringUtils.hasText(value) || value.length() > 512;
    }

}