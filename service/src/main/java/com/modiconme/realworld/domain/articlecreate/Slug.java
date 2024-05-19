package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.Locale;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class Slug implements ValueObject<String> {

    private static final String RFC3897_FORBIDDEN_CHARACTERS = "[!\"#№$%&'()*+,.\\\\/:;<=>?@\\[\\]^_·`{|}~]";
    private static final String WHITESPACES = "\\s+";

    private final String value;

    static Result<Slug> emerge(Title title) {
        if (title == null) {
            return Result.failure(ApiException.unprocessableEntity("Invalid title for slug"));
        }

        String slug = Normalizer.normalize(title.getValue(), Normalizer.Form.NFD)
                .trim()
                .replaceAll(RFC3897_FORBIDDEN_CHARACTERS, "")
                .replaceAll(WHITESPACES, "-")
                .toLowerCase(Locale.ENGLISH);

        if (isInvalid(slug)) {
            return Result.failure(ApiException.unprocessableEntity("Invalid title for slug: '%s'".formatted(title.getValue())));
        }

        return Result.success(new Slug(slug));
    }

    private static boolean isInvalid(String value) {
        return !StringUtils.hasText(value) || value.length() > 255;
    }
}