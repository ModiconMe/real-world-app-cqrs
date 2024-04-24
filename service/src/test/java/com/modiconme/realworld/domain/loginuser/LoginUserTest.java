package com.modiconme.realworld.domain.loginuser;

import com.modiconme.realworld.domain.common.Password;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.testcommon.PlainTextPasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginUserTest {

    private static final PlainTextPasswordEncoder PASSWORD_MATCHER = new PlainTextPasswordEncoder();

    @Test
    void success() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("mail@mail.ru");
        userEntity.setPassword("p@ssword");
        userEntity.setUsername("username");

        Result<LoginUser> result = LoginUser.emerge(userEntity, Password.emerge("p@ssword").getData(), PASSWORD_MATCHER);

        assertTrue(result.isSuccess());
        UserEntity resultEntity = result.getData().getUserEntity();
        assertEquals(userEntity.getEmail(), resultEntity.getEmail());
        assertEquals(userEntity.getPassword(), resultEntity.getPassword());
        assertEquals(userEntity.getUsername(), resultEntity.getUsername());
    }

    @Test
    void failureWhenUserNotFound() {
        Result<LoginUser> result = LoginUser.emerge(null, Password.emerge("p@ssword").getData(),
                PASSWORD_MATCHER);
        assertFalse(result.isSuccess());
        assertEquals("User not found", result.getError().getMessage());
    }

    @Test
    void failureWhenPasswordDoesNotMatch() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("mail@mail.ru");
        userEntity.setPassword("p@ssword");
        userEntity.setUsername("username");

        Result<LoginUser> result = LoginUser.emerge(userEntity, Password.emerge("p@ssword1").getData(),
                PASSWORD_MATCHER);
        assertFalse(result.isSuccess());
        assertEquals("Password does not match", result.getError().getMessage());
    }

}