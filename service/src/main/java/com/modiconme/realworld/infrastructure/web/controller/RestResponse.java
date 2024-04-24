package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.common.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class RestResponse<T> {

    private final T data;
    private final String error;

    public static <T> RestResponse<T> of(Result<T> result) {
        return result.isSuccess()
                ? new RestResponse<>(result.getData(), null)
                : new RestResponse<>(null, result.getError().getMessage());
    }
}
