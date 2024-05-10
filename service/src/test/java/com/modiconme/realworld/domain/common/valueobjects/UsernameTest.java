package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.it.base.TestDataGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    static Stream<Arguments> failure() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of(TestDataGenerator.uniqString(65))
        );
    }

    static Stream<Arguments> success() {
        return Stream.of(
                Arguments.of("username")
        );
    }

    @ParameterizedTest
    @MethodSource
    void success(String username) {
        Result<Username> result = Username.emerge(username);

        assertTrue(result.isSuccess());
        assertEquals(username, result.getData().getValue());
    }

    @ParameterizedTest
    @MethodSource
    @NullSource
    void failure(String username) {
        Result<Username> result = Username.emerge(username);

        assertFalse(result.isSuccess());
        assertEquals("Invalid username: '%s'".formatted(username), result.getError().getMessage());
    }

}