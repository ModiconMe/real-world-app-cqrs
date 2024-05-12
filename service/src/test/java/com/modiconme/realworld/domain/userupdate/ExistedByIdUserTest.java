package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExistedByIdUserTest {

    @Test
    void success() {
        Result<ExistedByIdUser> result = ExistedByIdUser.emerge(
                1, "username", "email@mail.com", "password", "bio", null
        );

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().getUserId().getValue());
        assertEquals("username", result.getData().getUsername().getValue());
        assertEquals("email@mail.com", result.getData().getEmail().getValue());
        assertEquals("password", result.getData().getEncodedPassword().getValue());
        assertEquals(Optional.of("bio"), result.getData().getBio().getValue());
        assertEquals(Optional.empty(), result.getData().getImage().getValue());
    }

    @Test
    void failure() {
        Result<ExistedByIdUser> result = ExistedByIdUser.emerge(
                0, "username", "email@mail.com", "password", "bio", null
        );

        assertTrue(result.isFailure());
        assertEquals("User not found", result.getError().getMessage());
    }
}