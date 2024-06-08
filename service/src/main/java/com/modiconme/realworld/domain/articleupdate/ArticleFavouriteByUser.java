package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class ArticleFavouriteByUser implements ValueObject<Boolean> {

    private final boolean value;

    public static ArticleFavouriteByUser of(boolean value) {
        return new ArticleFavouriteByUser(value);
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
