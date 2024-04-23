package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.common.Result;
import lombok.Getter;

@Getter
public final class RestResponse<T> {

    private final T data;
    private final String error;

    public static <T> RestResponse<T> of(Result<T> result) {
        return result.isSuccess()
                ? new RestResponse<>(result.getData(), null)
                : new RestResponse<>(null, result.getError().getMessage());
    }

    private RestResponse(T data, String error) {
        this.data = data;
        this.error = error;
    }
}
