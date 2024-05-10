package com.modiconme.realworld.domain.userlogin;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Password;
import com.modiconme.realworld.testcommon.PlainTextPasswordEncoder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class AuthorizedUserTest {

    private static final PlainTextPasswordEncoder PASSWORD_MATCHER = new PlainTextPasswordEncoder();

    @Test
    void success() {
        ExistedByEmailUser existedByEmailUser = ExistedByEmailUser.emerge(
                1, "username", "mail@mail.ru", "p@ssword", null, null
        ).getData();

        Result<AuthorizedUser> result = AuthorizedUser.emerge(
                existedByEmailUser,
                Password.emerge("p@ssword").getData(),
                PASSWORD_MATCHER
        );

        assertTrue(result.isSuccess());
        ExistedByEmailUser resultUser = result.getData().getExistedByEmailUser();
        assertEquals(resultUser, existedByEmailUser);
    }

    @Test
    void failureWhenPasswordDoesNotMatch() {
        ExistedByEmailUser existedByEmailUser = ExistedByEmailUser.emerge(
                1, "username", "mail@mail.ru", "p@ssword", null, null
        ).getData();

        Result<AuthorizedUser> result = AuthorizedUser.emerge(
                existedByEmailUser,
                Password.emerge("p@ssword1").getData(),
                PASSWORD_MATCHER
        );

        assertFalse(result.isSuccess());
        assertEquals("Password does not match", result.getError().getMessage());
    }

}