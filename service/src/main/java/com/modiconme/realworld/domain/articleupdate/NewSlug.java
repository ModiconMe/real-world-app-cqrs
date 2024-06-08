package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.articlecreate.Slug;
import com.modiconme.realworld.domain.articlecreate.Title;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class NewSlug implements ValueObject<Optional<Slug>> {

    private final Optional<Slug> value;

    public static Result<NewSlug> emerge(String value) {
        if (value == null) {
            return Result.success(new NewSlug(Optional.empty()));
        }

        return Title.emerge(value).flatMap(Slug::emerge).map(it -> new NewSlug(Optional.of(it)));
    }
}
