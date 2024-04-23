package com.modiconme.realworld.domain.registeruser;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatedRegisterUserRequestTest {

    @Test
    void success() {
        var unvalidatedRegisterUserRequest = new UnvalidatedRegisterUserRequest("mail@mail.ru", "username", "password");
        Result<ValidatedRegisterUserRequest> result = ValidatedRegisterUserRequest.emerge(unvalidatedRegisterUserRequest);
        assertTrue(result.isSuccess());
        assertEquals(result.getData().getEmail().getValue(), unvalidatedRegisterUserRequest.email());
        assertEquals(result.getData().getUsername().getValue(), unvalidatedRegisterUserRequest.username());
        assertEquals(result.getData().getPassword().getValue(), unvalidatedRegisterUserRequest.password());
    }

    @Test
    void failure() {
        var unvalidatedRegisterUserRequest = new UnvalidatedRegisterUserRequest("mailmail.ru", "username", "password");
        Result<ValidatedRegisterUserRequest> result = ValidatedRegisterUserRequest.emerge(unvalidatedRegisterUserRequest);
        assertFalse(result.isSuccess());
        assertEquals("Invalid email: '%s'".formatted(unvalidatedRegisterUserRequest.email()), result.getError().getMessage());
    }
}