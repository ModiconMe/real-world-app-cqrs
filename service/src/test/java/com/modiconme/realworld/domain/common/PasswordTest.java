package com.modiconme.realworld.domain.common;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @ParameterizedTest
    @MethodSource
    void success(String password) {
        Result<Password> result = Password.emerge(password);
        assertTrue(result.isSuccess());
        assertEquals(password, result.getData().getValue());
    }

    @ParameterizedTest
    @MethodSource
    @NullSource
    void failure(String password) {
        Result<Password> result = Password.emerge(password);
        assertFalse(result.isSuccess());
        assertEquals("Invalid password: '%s'".formatted(password), result.getError().getMessage());
    }

    static Stream<Arguments> success() {
        return Stream.of(
                Arguments.of("password")
        );
    }

    static Stream<Arguments> failure() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345")
        );
    }

}