package com.modiconme.realworld.domain.userregister;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.testcommon.PlainTextPasswordEncoder;
import org.junit.jupiter.api.Test;

import static com.modiconme.realworld.domain.userregister.ValidatedRegisterUserRequest.emerge;
import static org.junit.jupiter.api.Assertions.*;

class ValidatedRegisterUserRequestTest {

    private static final PlainTextPasswordEncoder PASSWORD_MATCHER = new PlainTextPasswordEncoder();

    @Test
    void success() {
        Result<ValidatedRegisterUserRequest> result = emerge(
                new RegisterUserRequest("mail@mail.ru", "username", "password"),
                PASSWORD_MATCHER
        );

        assertTrue(result.isSuccess());
        assertEquals("mail@mail.ru", result.getData().getEmail().getValue());
        assertEquals("username", result.getData().getUsername().getValue());
        assertEquals("password", result.getData().getPassword().getValue());
    }

    @Test
    void failure() {
        Result<ValidatedRegisterUserRequest> result = emerge(
                new RegisterUserRequest("mailmail.ru", "username", "password"),
                PASSWORD_MATCHER
        );

        assertFalse(result.isSuccess());
        assertEquals("Invalid email: '%s'".formatted("mailmail.ru"), result.getError().getMessage());
    }

}