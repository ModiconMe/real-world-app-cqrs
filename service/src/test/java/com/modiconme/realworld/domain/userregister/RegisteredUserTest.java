package com.modiconme.realworld.domain.userregister;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.testcommon.PlainTextPasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisteredUserTest {

    private static final PasswordEncoder PASSWORD_ENCODER = new PlainTextPasswordEncoder();

    @Test
    void success() {
        Result<RegisteredUser> result = RegisteredUser.emerge(
                1,
                ValidatedRegisterUserRequest.emerge(
                        new RegisterUserRequest("email@mail.com", "username", "password"),
                        PASSWORD_ENCODER
                ).getData()
        );

        assertTrue(result.isSuccess());
        assertEquals("username", result.getData().getUsername().getValue());
        assertEquals("email@mail.com", result.getData().getEmail().getValue());
        assertEquals("password", result.getData().getEncodedPassword().getValue());
    }

    @Test
    void failure() {
        Result<RegisteredUser> result = RegisteredUser.emerge(
                -1,
                ValidatedRegisterUserRequest.emerge(
                        new RegisterUserRequest("email@mail.com", "username", "password"),
                        PASSWORD_ENCODER
                ).getData()
        );

        assertTrue(result.isFailure());
        assertEquals("User not found", result.getError().getMessage());
    }
}