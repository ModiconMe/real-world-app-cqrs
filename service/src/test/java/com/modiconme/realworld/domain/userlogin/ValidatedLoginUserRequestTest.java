package com.modiconme.realworld.domain.userlogin;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatedLoginUserRequestTest {

    @Test
    void success() {
        Result<ValidatedLoginUserRequest> result = ValidatedLoginUserRequest.emerge(
                new LoginUserRequest("mail@mail.ru", "password")
        );

        assertTrue(result.isSuccess());
        assertEquals("mail@mail.ru", result.getData().getEmail().getValue());
        assertEquals("password", result.getData().getPassword().getValue());
    }

    @Test
    void failure() {
        Result<ValidatedLoginUserRequest> result = ValidatedLoginUserRequest.emerge(
                new LoginUserRequest("mailmail.ru", "password")
        );

        assertFalse(result.isSuccess());
        assertEquals("Invalid email: '%s'".formatted("mailmail.ru"), result.getError().getMessage());
    }

}