package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FolloweeIdTest {

    @Test
    void success() {
        Result<FolloweeId> result = FolloweeId.emerge(1L);
        assertTrue(result.isSuccess());
        assertEquals(1L, result.getData().getValue());
    }

    @Test
    void failure() {
        Result<FolloweeId> result = FolloweeId.emerge(-1);
        assertTrue(result.isFailure());
        assertEquals("Followee not found", result.getError().getMessage());
    }

}