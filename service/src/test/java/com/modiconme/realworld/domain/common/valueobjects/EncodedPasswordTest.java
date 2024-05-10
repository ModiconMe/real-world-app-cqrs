package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.testcommon.PlainTextPasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EncodedPasswordTest {

    private static final PlainTextPasswordEncoder PASSWORD_MATCHER = new PlainTextPasswordEncoder();

    @Test
    void success() {
        Result<EncodedPassword> result = EncodedPassword.emerge(
                Password.emerge("password").getData(),
                PASSWORD_MATCHER
        );

        assertTrue(result.isSuccess());
        assertEquals("password", result.getData().getValue());
    }

}