package com.modiconme.realworld.domain.loginuser;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatedLoginUserRequestTest {

    @Test
    void success() {
        var unvalidatedLoginUserRequest = new UnvalidatedLoginUserRequest("mail@mail.ru", "password");
        Result<ValidatedLoginUserRequest> result = ValidatedLoginUserRequest.emerge(unvalidatedLoginUserRequest);
        assertTrue(result.isSuccess());
        assertEquals(result.getData().getEmail().getValue(), unvalidatedLoginUserRequest.email());
        assertEquals(result.getData().getPassword().getValue(), unvalidatedLoginUserRequest.password());
    }

    @Test
    void failure() {
        var unvalidatedLoginUserRequest = new UnvalidatedLoginUserRequest("mailmail.ru", "password");
        Result<ValidatedLoginUserRequest> result = ValidatedLoginUserRequest.emerge(unvalidatedLoginUserRequest);
        assertFalse(result.isSuccess());
        assertEquals("Invalid email: '%s'".formatted(unvalidatedLoginUserRequest.email()), result.getError().getMessage());
    }

}