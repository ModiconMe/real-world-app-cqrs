package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import com.modiconme.realworld.it.base.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NewUsernameTest {

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
    @NullSource
    void success(String username) {
        Result<NewUsername> result = NewUsername.emerge(username);

        assertTrue(result.isSuccess());
        assertEquals(Optional.ofNullable(username), result.getData().getValue());
    }

    @Test
    void successMapToUsername() {
        Result<Username> oldUsername = Username.emerge("username1");
        Username result = NewUsername.emerge("username").getData()
                .mapToUsername(oldUsername.getData());

        assertEquals("username", result.getValue());
    }

    @Test
    void successKeepOldValueWhenNewValueIsNull() {
        Result<Username> oldUsername = Username.emerge("username1");
        Username result = NewUsername.emerge(null).getData()
                .mapToUsername(oldUsername.getData());

        assertEquals("username1", result.getValue());
    }

    @ParameterizedTest
    @MethodSource
    void failure(String username) {
        Result<NewUsername> result = NewUsername.emerge(username);

        assertFalse(result.isSuccess());
        assertEquals("Invalid username: '%s'".formatted(username), result.getError().getMessage());
    }

}