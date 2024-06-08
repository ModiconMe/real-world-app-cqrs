package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.articlecreate.Body;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ValueObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class NewBody implements ValueObject<Optional<Body>> {

    private final Optional<Body> value;

    public static Result<NewBody> emerge(String value) {
        if (value == null) {
            return Result.success(new NewBody(Optional.empty()));
        }

        return Body.emerge(value).map(it -> new NewBody(Optional.of(it)));
    }
}
