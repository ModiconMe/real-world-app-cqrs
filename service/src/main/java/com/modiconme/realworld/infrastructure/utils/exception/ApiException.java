package com.modiconme.realworld.infrastructure.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    private ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public static ApiException exception(HttpStatus status, String message, Object... args) {
        return new ApiException(status, format(message, args));
    }

    public static ApiException exception(HttpStatus status, String message, Throwable cause, Object... args) {
        return new ApiException(format(message, args), cause, status);
    }

    public static ApiException notFound(String message, Object... args) {
        return exception(HttpStatus.NOT_FOUND, message, args);
    }

    public static ApiException unauthorized(String message, Object... args) {
        return exception(HttpStatus.UNAUTHORIZED, message, args);
    }

    public static ApiException forbidden(String message, Object... args) {
        return exception(HttpStatus.FORBIDDEN, message, args);
    }

    public static ApiException unprocessableEntity(String message, Object... args) {
        return exception(HttpStatus.UNPROCESSABLE_ENTITY, message, args);
    }

    public static ApiException internalError(String message, Throwable cause, Object... args) {
        return exception(HttpStatus.INTERNAL_SERVER_ERROR, message, cause, args);
    }

}
