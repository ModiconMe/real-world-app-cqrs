package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.articlecreate.Description;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class NewDescription implements ValueObject<Optional<Description>> {

    private final Optional<Description> value;

    public static Result<NewDescription> emerge(String value) {
        if (value == null) {
            return Result.success(new NewDescription(Optional.empty()));
        }

        return Description.emerge(value).map(it -> new NewDescription(Optional.of(it)));
    }
}
