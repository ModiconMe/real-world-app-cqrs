package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class TagList implements ValueObject<List<String>> {

    private final List<String> value;

    public static Result<TagList> emerge(List<String> value) {
        if (value == null) {
            return Result.failure(ApiException.unprocessableEntity("Invalid tag list"));
        }

        List<String> normalizedTags = value.stream().distinct().map(String::trim).toList();

        List<String> invalidTags = normalizedTags.stream().filter(TagList::isInvalid).toList();
        if (!invalidTags.isEmpty()) {
            return Result.failure(ApiException.unprocessableEntity("Invalid tags: '%s'".formatted(invalidTags)));
        }

        return Result.success(new TagList(normalizedTags));
    }

    private static boolean isInvalid(String value) {
        return !StringUtils.hasText(value) || value.length() > 128;
    }
}