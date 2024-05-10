package com.modiconme.realworld.infrastructure.security;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExistedByUsernameUserTest {

    @Test
    void success() {
        Result<ExistedByUsernameUser> result = ExistedByUsernameUser.emerge(
                1, "username", "email@mail.com", "hash"
        );

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().getUserId().getValue());
        assertEquals("username", result.getData().getUsername().getValue());
        assertEquals("email@mail.com", result.getData().getEmail().getValue());
        assertEquals("hash", result.getData().getEncodedPassword().getValue());
    }

    @Test
    void failure() {
        Result<ExistedByUsernameUser> result = ExistedByUsernameUser.emerge(
                -1, "username", "email@mail.com", "hash"
        );

        assertTrue(result.isFailure());
        assertEquals("User not found", result.getError().getMessage());
    }
}