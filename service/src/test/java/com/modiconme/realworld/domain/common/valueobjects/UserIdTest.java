package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserIdTest {

    @Test
    void success() {
        Result<UserId> result = UserId.emerge(1L);
        assertTrue(result.isSuccess());
        assertEquals(1L, result.getData().getValue());
    }

    @Test
    void failure() {
        Result<UserId> result = UserId.emerge(-1L);
        assertTrue(result.isFailure());
        assertEquals("User not found", result.getError().getMessage());
    }

}