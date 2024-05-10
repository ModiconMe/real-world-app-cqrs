package com.modiconme.realworld.domain.profileunfollow;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatedUnfollowProfileRequestTest {

    @Test
    void success() {
        Result<ValidatedUnfollowProfileRequest> result = ValidatedUnfollowProfileRequest.emerge(
                new UnfollowProfileRequest(1, "username")
        );

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().getUserId().getValue());
        assertEquals("username", result.getData().getProfileUsername().getValue());
    }

    @Test
    void failure() {
        Result<ValidatedUnfollowProfileRequest> result = ValidatedUnfollowProfileRequest.emerge(
                new UnfollowProfileRequest(-1, "username")
        );

        assertTrue(result.isFailure());
        assertEquals("User not found", result.getError().getMessage());
    }
}