package com.modiconme.realworld.domain.userlogin;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExistedByEmailUserTest {

    @Test
    void success() {
        Result<ExistedByEmailUser> result = ExistedByEmailUser.emerge(
                1, "username", "email@mail.ru", "hash", "bio", null
        );

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().getUserId().getValue());
        assertEquals("username", result.getData().getUsername().getValue());
        assertEquals("email@mail.ru", result.getData().getEmail().getValue());
        assertEquals("hash", result.getData().getEncodedPassword().getValue());
        assertEquals(Optional.of("bio"), result.getData().getBio().getValue());
        assertEquals(Optional.empty(), result.getData().getImage().getValue());
    }

    @Test
    void failure() {
        Result<ExistedByEmailUser> result = ExistedByEmailUser.emerge(
                -1, "username", "email@mail.ru", "hash", "bio", null
        );

        assertTrue(result.isFailure());
        assertEquals("User not found", result.getError().getMessage());
    }

}