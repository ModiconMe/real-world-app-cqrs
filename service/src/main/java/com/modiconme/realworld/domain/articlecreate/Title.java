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
public final class Title implements ValueObject<String> {

    private final String value;

    public static Result<Title> emerge(String value) {
        if (isInvalid(value)) {
            return Result.failure(ApiException.unprocessableEntity("Invalid title: '%s'".formatted(value)));
        }

        return Result.success(new Title(value.trim()));
    }

    private static boolean isInvalid(String value) {
        return !StringUtils.hasText(value) || value.length() > 255;
    }
}
