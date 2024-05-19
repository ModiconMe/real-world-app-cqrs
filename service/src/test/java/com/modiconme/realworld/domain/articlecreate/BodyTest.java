package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.it.base.TestDataGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BodyTest {

    static Stream<Arguments> failure() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of(TestDataGenerator.uniqString(513))
        );
    }

    static Stream<Arguments> success() {
        return Stream.of(
                Arguments.of("body"),
                Arguments.of(TestDataGenerator.uniqString(512))
        );
    }

    @ParameterizedTest
    @MethodSource
    void success(String body) {
        Result<Body> result = Body.emerge(body);

        assertTrue(result.isSuccess());
        assertEquals(body, result.getData().getValue());
    }

    @ParameterizedTest
    @MethodSource
    @NullSource
    void failure(String body) {
        Result<Body> result = Body.emerge(body);

        assertFalse(result.isSuccess());
        assertEquals("Invalid body: '%s'".formatted(body), result.getError().getMessage());
    }

}