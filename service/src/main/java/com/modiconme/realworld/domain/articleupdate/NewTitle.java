package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.articlecreate.Title;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class NewTitle implements ValueObject<Optional<Title>> {

    private final Optional<Title> value;

    public static Result<NewTitle> emerge(String value) {
        if (value == null) {
            return Result.success(new NewTitle(Optional.empty()));
        }

        return Title.emerge(value).map(it -> new NewTitle(Optional.of(it)));
    }
}
