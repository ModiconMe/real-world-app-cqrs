package com.modiconme.realworld.domain.profileunfollow;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FollowedProfileTest {

    @Test
    void success() {
        Result<FollowedProfile> result = FollowedProfile.emerge(1, "username", "bio", null, 2);

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().getId().getValue());
        assertEquals("username", result.getData().getUsername().getValue());
        assertEquals(Optional.of("bio"), result.getData().getBio().getValue());
        assertEquals(Optional.empty(), result.getData().getImage().getValue());
        assertEquals(2, result.getData().getFollowRelationId().getValue());
    }

    @Test
    void failure() {
        Result<FollowedProfile> result = FollowedProfile.emerge(0, "username", "bio", null, 2);

        assertTrue(result.isFailure());
        assertEquals("Followee not found", result.getError().getMessage());
    }
}