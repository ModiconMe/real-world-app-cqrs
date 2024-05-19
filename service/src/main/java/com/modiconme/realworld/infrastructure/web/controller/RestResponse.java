package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.common.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@RequiredArgsConstructor(access = PRIVATE)
public final class RestResponse<T> {

    private final T data;
    private final String error;

    public static <T> RestResponse<T> of(Result<T> result) {
        return result.mapBoth(
                it -> new RestResponse<>(it, null),
                it -> new RestResponse<>(null, it.getMessage())
        );
    }
}
