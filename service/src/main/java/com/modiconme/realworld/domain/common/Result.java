package com.modiconme.realworld.domain.common;

import lombok.Getter;
import lombok.SneakyThrows;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class Result<T> {

    private final T data;
    @Getter
    private final Throwable error;

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null);
    }

    public static <T> Result<T> failure(Throwable error) {
        return new Result<>(null, error);
    }

    public static <A, B, C> Result<Triplet<A, B, C>> zip(
            Result<A> a, Result<B> b, Result<C> c
    ) {
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

    public static <A, B, C, D> Result<Quartet<A, B, C, D>> zip(
            Result<A> a, Result<B> b, Result<C> c, Result<D> d
    ) {
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

    public boolean isSuccess() {
        return data != null && error == null;
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
        if (!isSuccess()) {
            return Result.failure(error);
        } else {
            @SuppressWarnings("unchecked")
            Result<U> r = (Result<U>) mapper.apply(data);
            return Objects.requireNonNull(r);
        }
    }

    private Result(T data, Throwable error) {
        this.data = data;
        this.error = error;
    }
}
