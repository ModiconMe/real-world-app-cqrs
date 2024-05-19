package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public final class ArticleId implements ValueObject<Long> {

    private final long value;

    public static Result<ArticleId> emerge(long id) {
        if (id <= 0) {
            return Result.failure(ApiException.notFound("Article not found"));
        }

        return Result.success(new ArticleId(id));
    }

    @Override
    public Long getValue() {
        return value;
    }
}
