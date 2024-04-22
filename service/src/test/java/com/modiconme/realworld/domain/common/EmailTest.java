package com.modiconme.realworld.domain.common;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @ParameterizedTest
    @MethodSource
    void success(String email) {
        assertDoesNotThrow(() -> Email.emerge(email));
    }

    @ParameterizedTest
    @MethodSource
    void failure(String email) {
        assertThrows(IllegalArgumentException.class, () -> Email.emerge(email), "Invalid email");
    }

    static Stream<Arguments> success() {
        return Stream.of(
                Arguments.of("test@modiconme.com"),
                Arguments.of("test@modiconme.ru"),
                Arguments.of("test@modiconme.org"),
                Arguments.of("test@gmail.com"),
                Arguments.of("test@gmail.ru"),
                Arguments.of("test@gmail.org")
        );
    }

    static Stream<Arguments> failure() {
        return Stream.of(
                Arguments.of("testmodiconme.com"),
                Arguments.of("test@modiconme.ru1"),
                Arguments.of("test@gmailcom"),
                Arguments.of("1test@gmail.ru")
        );
    }
}