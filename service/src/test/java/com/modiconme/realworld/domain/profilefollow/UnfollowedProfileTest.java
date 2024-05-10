package com.modiconme.realworld.domain.profilefollow;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnfollowedProfileTest {

    @Test
    void success() {
        Result<UnfollowedProfile> result = UnfollowedProfile.emerge(
                1L,
                UserId.emerge(2L).getData(),
                "username",
                null,
                "image"
        );

        assertTrue(result.isSuccess());
        assertEquals(1L, result.getData().getFolloweeId().getValue());
        assertEquals(2L, result.getData().getFollowerId().getValue());
        assertEquals("username", result.getData().getUsername().getValue());
        assertTrue(result.getData().getBio().getValue().isEmpty());
        assertEquals("image", result.getData().getImage().getValue().orElseThrow());
    }

    @Test
    void failure() {
        Result<UnfollowedProfile> result = UnfollowedProfile.emerge(
                -1L,
                // ^^ invalid value
                UserId.emerge(2L).getData(),
                "username",
                null,
                "image"
        );

        assertTrue(result.isFailure());
        assertEquals("Followee not found", result.getError().getMessage());
    }
}