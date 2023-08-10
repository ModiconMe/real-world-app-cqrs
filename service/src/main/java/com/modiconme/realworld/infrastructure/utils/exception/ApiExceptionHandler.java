package com.modiconme.realworld.infrastructure.utils.exception;

import com.modiconme.realworld.dto.ApiExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ApiExceptionDto> handleApiRequestException(ApiException e) {
        return new ResponseEntity<>(new ApiExceptionDto(List.of(e.getMessage())), e.getStatus());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionDto> handleApiRequestValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                new ApiExceptionDto(List.of(Objects.requireNonNull(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()))),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

}
