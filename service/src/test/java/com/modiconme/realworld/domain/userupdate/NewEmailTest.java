package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Email;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NewEmailTest {

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

    @ParameterizedTest
    @MethodSource
    @NullSource
    void success(String email) {
        Result<NewEmail> result = NewEmail.emerge(email);

        assertTrue(result.isSuccess());
        assertEquals(Optional.ofNullable(email), result.getData().getValue());
    }

    @Test
    void successMapToEmail() {
        Result<Email> oldEmail = Email.emerge("email1@example.com");
        Email result = NewEmail.emerge("email@example.com").getData()
                .mapToEmail(oldEmail.getData());

        assertEquals("email@example.com", result.getValue());
    }

    @Test
    void successKeepOldValueWhenNewValueIsNull() {
        Result<Email> oldEmail = Email.emerge("email1@example.com");
        Email result = NewEmail.emerge(null).getData()
                .mapToEmail(oldEmail.getData());

        assertEquals("email1@example.com", result.getValue());
    }

    @ParameterizedTest
    @MethodSource
    void failure(String email) {
        Result<NewEmail> result = NewEmail.emerge(email);

        assertFalse(result.isSuccess());
        assertEquals("Invalid email: '%s'".formatted(email), result.getError().getMessage());
    }
}