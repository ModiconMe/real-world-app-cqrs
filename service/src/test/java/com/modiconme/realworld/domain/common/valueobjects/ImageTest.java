package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.it.base.TestDataGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageTest {

    static Stream<Arguments> success() {
        return Stream.of(
                Arguments.of(TestDataGenerator.uniqString(64))
        );
    }

    static Stream<Arguments> failure() {
        return Stream.of(
                Arguments.of(TestDataGenerator.uniqString(65))
        );
    }

    @ParameterizedTest
    @MethodSource
    @NullSource
    @EmptySource
    void success(String image) {
        Result<Image> result = Image.emerge(image);
        assertTrue(result.isSuccess());
        assertEquals(Optional.ofNullable(image), result.getData().getValue());
    }

    @ParameterizedTest
    @MethodSource
    void failure(String image) {
        Result<Image> result = Image.emerge(image);
        assertTrue(result.isFailure());
        assertEquals("Invalid image value", result.getError().getMessage());
    }
}