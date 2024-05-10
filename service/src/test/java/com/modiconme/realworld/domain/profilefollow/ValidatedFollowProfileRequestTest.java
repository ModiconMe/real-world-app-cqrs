package com.modiconme.realworld.domain.profilefollow;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatedFollowProfileRequestTest {

    @Test
    void success() {
        Result<ValidatedFollowProfileRequest> result = ValidatedFollowProfileRequest.emerge(
                new FollowProfileRequest(1, "username")
        );

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().getCurrentUserId().getValue());
        assertEquals("username", result.getData().getProfileUsername().getValue());
    }

    @Test
    void failure() {
        Result<ValidatedFollowProfileRequest> result = ValidatedFollowProfileRequest.emerge(
                new FollowProfileRequest(-1, "username")
        );

        assertTrue(result.isFailure());
        assertEquals("User not found", result.getError().getMessage());
    }
}