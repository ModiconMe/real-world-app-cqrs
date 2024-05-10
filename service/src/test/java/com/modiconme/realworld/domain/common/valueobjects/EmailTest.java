package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @ParameterizedTest
    @MethodSource
    void success(String email) {
        Result<Email> result = Email.emerge(email);

        assertTrue(result.isSuccess());
        assertEquals(email, result.getData().getValue());
    }

    @ParameterizedTest
    @MethodSource
    void failure(String email) {
        Result<Email> result = Email.emerge(email);

        assertFalse(result.isSuccess());
        assertEquals("Invalid email: '%s'".formatted(email), result.getError().getMessage());
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