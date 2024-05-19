package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.it.base.TestDataGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionTest {

    static Stream<Arguments> failure() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of(TestDataGenerator.uniqString(513))
        );
    }

    static Stream<Arguments> success() {
        return Stream.of(
                Arguments.of("description"),
                Arguments.of(TestDataGenerator.uniqString(512))
        );
    }

    @ParameterizedTest
    @MethodSource
    void success(String description) {
        Result<Description> result = Description.emerge(description);

        assertTrue(result.isSuccess());
        assertEquals(description, result.getData().getValue());
    }

    @ParameterizedTest
    @MethodSource
    @NullSource
    void failure(String description) {
        Result<Description> result = Description.emerge(description);

        assertFalse(result.isSuccess());
        assertEquals("Invalid description: '%s'".formatted(description), result.getError().getMessage());
    }

}