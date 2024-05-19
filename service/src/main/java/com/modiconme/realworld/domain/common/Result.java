package com.modiconme.realworld.domain.common;

import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import com.modiconme.realworld.infrastructure.web.controller.RestResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javatuples.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class Result<T> {

    private final T data;
    @Getter
    private final Throwable error;
    @Getter
    private final HttpStatus status;
    private final boolean isSuccess;

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, HttpStatus.OK, true);
    }

    public static <T> Result<T> failure(Throwable error) {
        if (error instanceof ApiException apiException) {
            return new Result<>(null, error, apiException.getStatus(), false);
        }
        return new Result<>(null, ApiException.internalError("Извините, мы не смогли обработать ваш запрос"),
                HttpStatus.UNPROCESSABLE_ENTITY, false);
    }

    public static <T> Result<T> runCatching(Supplier<T> supplier, Consumer<Throwable> onErrorAction) {
        Objects.requireNonNull(supplier);
        try {
            return Result.success(supplier.get());
        } catch (Throwable e) {
            onErrorAction.accept(e);
            return Result.failure(e);
        }
    }

    public static <A, B> Result<Pair<A, B>> zip(Result<A> a, Result<B> b) {
        if (Stream.of(a, b).noneMatch(Result::isFailure)) {
            Pair<A, B> with = Pair.with(a.data, b.data);
            return success(with);
        } else {
            Result<?> result = Stream.of(a, b)
                    .filter(Result::isFailure)
                    .findFirst()
                    .orElseThrow();
            return failure(result.getError());
        }
    }

    public static <A, B, C> Result<Triplet<A, B, C>> zip(Result<A> a, Result<B> b, Result<C> c) {
        if (Stream.of(a, b, c).noneMatch(Result::isFailure)) {
            Triplet<A, B, C> with = Triplet.with(a.data, b.data, c.data);
            return success(with);
        } else {
            Result<?> result = Stream.of(a, b, c)
                    .filter(Result::isFailure)
                    .findFirst()
                    .orElseThrow();
            return failure(result.getError());
        }
    }

    public static <A, B, C, D> Result<Quartet<A, B, C, D>> zip(Result<A> a, Result<B> b, Result<C> c,
                                                               Result<D> d) {
        if (Stream.of(a, b, c, d).noneMatch(Result::isFailure)) {
            Quartet<A, B, C, D> with = Quartet.with(a.data, b.data, c.data, d.data);
            return success(with);
        } else {
            Result<?> result = Stream.of(a, b, c, d)
                    .filter(Result::isFailure)
                    .findFirst()
                    .orElseThrow();
            return failure(result.getError());
        }
    }

    public static <A, B, C, D, E> Result<Quintet<A, B, C, D, E>> zip(Result<A> a, Result<B> b, Result<C> c,
                                                                     Result<D> d, Result<E> e) {
        if (Stream.of(a, b, c, d).noneMatch(Result::isFailure)) {
            Quintet<A, B, C, D, E> with = Quintet.with(a.data, b.data, c.data, d.data, e.data);
            return success(with);
        } else {
            Result<?> result = Stream.of(a, b, c, d)
                    .filter(Result::isFailure)
                    .findFirst()
                    .orElseThrow();
            return failure(result.getError());
        }
    }

    public static <A, B, C, D, E, F> Result<Sextet<A, B, C, D, E, F>> zip(Result<A> a, Result<B> b, Result<C> c,
                                                                          Result<D> d, Result<E> e, Result<F> f) {
        if (Stream.of(a, b, c, d, f).noneMatch(Result::isFailure)) {
            Sextet<A, B, C, D, E, F> with = Sextet.with(a.data, b.data, c.data, d.data, e.data, f.data);
            return success(with);
        } else {
            Result<?> result = Stream.of(a, b, c, d)
                    .filter(Result::isFailure)
                    .findFirst()
                    .orElseThrow();
            return failure(result.getError());
        }
    }

    public static <A> Result<List<A>> zip(List<Result<A>> list) {
        if (list.stream().noneMatch(Result::isFailure)) {
            return success(list.stream().map(Result::getData).toList());
        } else {
            Result<?> result = list.stream()
                    .filter(Result::isFailure)
                    .findFirst()
                    .orElseThrow();
            return failure(result.getError());
        }
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    @SneakyThrows
    public T getData() {
        if (isFailure()) {
            throw error;
        }
        return data;
    }

    public <U> Result<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (isSuccess()) {
            return Result.success(mapper.apply(data));
        } else {
            return Result.failure(error);
        }
    }

    public <U> Result<U> flatMap(Function<? super T, ? extends Result<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if (isSuccess()) {
            @SuppressWarnings("unchecked")
            Result<U> r = (Result<U>) mapper.apply(data);
            return Objects.requireNonNull(r);
        } else {
            return Result.failure(error);
        }
    }

    public <U> U mapBoth(Function<? super T, ? extends U> successMapper,
                         Function<Throwable, ? extends U> errorMapper) {
        Objects.requireNonNull(successMapper);
        Objects.requireNonNull(errorMapper);
        if (isSuccess()) {
            return successMapper.apply(data);
        } else {
            return errorMapper.apply(error);
        }
    }

    public <U> Result<Pair<T, U>> combine(U other) {
        Objects.requireNonNull(other);
        if (isSuccess()) {
            return Result.success(new Pair<>(data, other));
        } else {
            return Result.failure(error);
        }
    }

    public Result<T> ensure(Predicate<T> predicate, Result<T> failure) {
        Objects.requireNonNull(predicate);
        if (isSuccess()) {
            return predicate.test(data) ? this : failure;
        } else {
            return Result.failure(error);
        }
    }

    public Result<T> ensure(Predicate<T> predicate,
                            Function<T, Throwable> failure) {
        Objects.requireNonNull(predicate);
        if (isSuccess()) {
            return predicate.test(data) ? this : Result.failure(failure.apply(data));
        } else {
            return Result.failure(error);
        }
    }

    public <U> Result<U> flatMap(Function<? super T, ? extends Result<? extends U>> mapper,
                                 Consumer<? super T> logError) {
        Objects.requireNonNull(mapper);
        if (isSuccess()) {
            @SuppressWarnings("unchecked")
            Result<U> r = (Result<U>) mapper.apply(data);
            return Objects.requireNonNull(r);
        } else {
            logError.accept(data);
            return Result.failure(error);
        }
    }

    public Result<T> onSuccess(Consumer<? super T> successAction) {
        if (isSuccess()) {
            successAction.accept(data);
        }
        return this;
    }

    public Result<T> tryOnSuccess(Consumer<? super T> action, Consumer<Throwable> onErrorAction) {
        Objects.requireNonNull(action);
        if (isSuccess()) {
            try {
                action.accept(data);
                return Result.success(data);
            } catch (Throwable e) {
                onErrorAction.accept(e);
                return Result.failure(e);
            }
        } else {
            return Result.failure(error);
        }
    }

    public Result<T> onFailure(Consumer<? super Throwable> failureAction) {
        if (isFailure()) {
            failureAction.accept(error);
        }
        return this;
    }

    public Result<T> onBoth(
            Consumer<? super T> successAction,
            Consumer<? super Throwable> failureAction
    ) {
        if (isSuccess()) {
            successAction.accept(data);
        }

        if (isFailure()) {
            failureAction.accept(error);
        }
        return this;
    }

    public ResponseEntity<RestResponse<T>> toRestResponse() {
        RestResponse<T> restResponse = RestResponse.of(this);
        return this.isSuccess()
                ? ResponseEntity.ok(restResponse)
                : ResponseEntity.status(getStatus()).body(restResponse);
    }

}
