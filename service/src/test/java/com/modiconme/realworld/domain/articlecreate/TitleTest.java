package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.it.base.TestDataGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TitleTest {

    static Stream<Arguments> failure() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of(TestDataGenerator.uniqString(256))
        );
    }

    static Stream<Arguments> success() {
        return Stream.of(
                Arguments.of("title"),
                Arguments.of(TestDataGenerator.uniqString(255))
        );
    }

    @ParameterizedTest
    @MethodSource
    void success(String title) {
        Result<Title> result = Title.emerge(title);

        assertTrue(result.isSuccess());
        assertEquals(title, result.getData().getValue());
    }

    @ParameterizedTest
    @MethodSource
    @NullSource
    void failure(String title) {
        Result<Title> result = Title.emerge(title);

        assertFalse(result.isSuccess());
        assertEquals("Invalid title: '%s'".formatted(title), result.getError().getMessage());
    }

}