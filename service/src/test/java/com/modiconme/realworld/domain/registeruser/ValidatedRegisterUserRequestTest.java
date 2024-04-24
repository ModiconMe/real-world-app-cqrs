package com.modiconme.realworld.domain.registeruser;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.testcommon.PlainTextPasswordEncoder;
import org.junit.jupiter.api.Test;

import static com.modiconme.realworld.domain.registeruser.ValidatedRegisterUserRequest.emerge;
import static org.junit.jupiter.api.Assertions.*;

class ValidatedRegisterUserRequestTest {

    private static final PlainTextPasswordEncoder PASSWORD_MATCHER = new PlainTextPasswordEncoder();

    @Test
    void success() {
        var unvalidatedRegisterUserRequest = new UnvalidatedRegisterUserRequest("mail@mail.ru", "username", "password");
        Result<ValidatedRegisterUserRequest> result = emerge(unvalidatedRegisterUserRequest,
                PASSWORD_MATCHER);
        assertTrue(result.isSuccess());
        assertEquals(result.getData().getEmail().getValue(), unvalidatedRegisterUserRequest.email());
        assertEquals(result.getData().getUsername().getValue(), unvalidatedRegisterUserRequest.username());
        assertEquals(result.getData().getPassword().getValue(), unvalidatedRegisterUserRequest.password());
    }

    @Test
    void failure() {
        var unvalidatedRegisterUserRequest = new UnvalidatedRegisterUserRequest("mailmail.ru", "username", "password");
        Result<ValidatedRegisterUserRequest> result = emerge(unvalidatedRegisterUserRequest,
                PASSWORD_MATCHER);
        assertFalse(result.isSuccess());
        assertEquals("Invalid email: '%s'".formatted(unvalidatedRegisterUserRequest.email()), result.getError().getMessage());
    }

}