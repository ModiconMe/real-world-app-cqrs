package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatedUpdateUserRequestTest {

    @Test
    void success() {
        Result<ValidatedUpdateUserRequest> result = ValidatedUpdateUserRequest.emerge(
                1, new UpdateUserRequest("email@mail.com", null, "password", "bio", null)
        );

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().getUserId().getValue());
        assertEquals(Optional.of("email@mail.com"), result.getData().getEmail().getValue());
        assertEquals(Optional.empty(), result.getData().getUsername().getValue());
        assertEquals(Optional.of("password"), result.getData().getPassword().getValue());
        assertEquals(Optional.of("bio"), result.getData().getBio().getValue());
        assertEquals(Optional.empty(), result.getData().getImage().getValue());
    }

    @Test
    void failure() {
        Result<ValidatedUpdateUserRequest> result = ValidatedUpdateUserRequest.emerge(
                0, new UpdateUserRequest("email@mail.com", null, "password", "bio", null)
        );

        assertTrue(result.isFailure());
        assertEquals("User not found", result.getError().getMessage());
    }
}
