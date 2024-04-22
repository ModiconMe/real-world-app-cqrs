package com.modiconme.realworld.domain.common;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    @ParameterizedTest
    @MethodSource
    void success(String username) {
        Result<Username> result = Username.emerge(username);
        assertTrue(result.isSuccess());
    }

    @ParameterizedTest
    @MethodSource
    @NullSource
    void failure(String username) {
        Result<Username> result = Username.emerge(username);
        assertFalse(result.isSuccess());
        assertEquals("Invalid username: '%s'".formatted(username), result.getError().getMessage());
    }

    static Stream<Arguments> success() {
        return Stream.of(
                Arguments.of("username")
        );
    }

    static Stream<Arguments> failure() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of("123456789012345678901234567890123456789012345678901234567890123451234567890123456789012345678901234567890123456789012345678901234")
        );
    }

}