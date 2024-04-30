package com.modiconme.realworld.domain.getprofile;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatedGetProfileRequestTest {

    @Test
    void success() {
        var getProfileRequest = new GetProfileRequest("username1", "username2");
        Result<ValidatedGetProfileRequest> result = ValidatedGetProfileRequest.emerge(getProfileRequest);
        assertTrue(result.isSuccess());
        assertEquals(result.getData().getProfileUsername().getValue(), getProfileRequest.profileUsername());
        assertEquals(result.getData().getCurrentUserUsername().getValue(), getProfileRequest.currentUsername());
    }

    @Test
    void failure() {
        var getProfileRequest = new GetProfileRequest("", "username2");
        Result<ValidatedGetProfileRequest> result = ValidatedGetProfileRequest.emerge(getProfileRequest);
        assertFalse(result.isSuccess());
        assertEquals("Invalid username: '%s'".formatted(getProfileRequest.currentUsername()), result.getError().getMessage());
    }

}