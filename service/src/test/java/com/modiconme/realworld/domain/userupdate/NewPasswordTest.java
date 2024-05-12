package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.EncodedPassword;
import com.modiconme.realworld.domain.common.valueobjects.Password;
import com.modiconme.realworld.testcommon.PlainTextPasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NewPasswordTest {

    private static final PasswordEncoder PASSWORD_ENCODER = new PlainTextPasswordEncoder();

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

    @ParameterizedTest
    @MethodSource
    @NullSource
    void success(String password) {
        Result<NewPassword> result = NewPassword.emerge(password);

        assertTrue(result.isSuccess());
        assertEquals(Optional.ofNullable(password), result.getData().getValue());
    }

    @Test
    void successMapToPassword() {
        Result<EncodedPassword> oldPassword = Password.emerge("password1").getData().encode(PASSWORD_ENCODER);
        EncodedPassword result = NewPassword.emerge("password").getData()
                .mapToEncodedPassword(PASSWORD_ENCODER, oldPassword.getData());

        assertEquals("password", result.getValue());
    }

    @Test
    void successKeepOldValueWhenNewValueIsNull() {
        Result<EncodedPassword> oldPassword = Password.emerge("password1").getData().encode(PASSWORD_ENCODER);
        EncodedPassword result = NewPassword.emerge(null).getData()
                .mapToEncodedPassword(PASSWORD_ENCODER, oldPassword.getData());

        assertEquals("password1", result.getValue());
    }

    @ParameterizedTest
    @MethodSource
    void failure(String password) {
        Result<Password> result = Password.emerge(password);

        assertFalse(result.isSuccess());
        assertEquals("Invalid password: '%s'".formatted(password), result.getError().getMessage());
    }

}