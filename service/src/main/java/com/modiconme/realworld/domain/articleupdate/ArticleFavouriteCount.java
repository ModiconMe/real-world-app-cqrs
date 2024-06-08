package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class ArticleFavouriteCount implements ValueObject<Long> {

    private final long value;

    public static Result<ArticleFavouriteCount> emerge(long value) {
        if (value < 0) {
            return Result.failure(ApiException.unprocessableEntity("Invalid favourite count"));
        }

        return Result.success(new ArticleFavouriteCount(value));
    }

    @Override
    public Long getValue() {
        return value;
    }
}
