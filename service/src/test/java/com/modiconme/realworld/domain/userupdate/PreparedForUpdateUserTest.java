package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.testcommon.PlainTextPasswordEncoder;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PreparedForUpdateUserTest {

    private static final PasswordEncoder PASSWORD_ENCODER = new PlainTextPasswordEncoder();

    @Test
    void success() {
        PreparedForUpdateUser result = PreparedForUpdateUser.of(
                ValidatedUpdateUserRequest.emerge(
                        1, new UpdateUserRequest("email@mail.com", null, "password", "bio", null)
                ).getData(),
                ExistedByIdUser.emerge(
                        1, "username", "email@mail.com", "hash", null, null
                ).getData(),
                PASSWORD_ENCODER
        );

        assertEquals(1, result.getUserId().getValue());
        assertEquals("email@mail.com", result.getEmail().getValue());
        assertEquals("username", result.getUsername().getValue());
        assertEquals("password", result.getPassword().getValue());
        assertEquals(Optional.of("bio"), result.getBio().getValue());
        assertEquals(Optional.empty(), result.getImage().getValue());
    }
}