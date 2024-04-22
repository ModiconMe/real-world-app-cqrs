package com.modiconme.realworld.domain.registeruser;

import com.modiconme.realworld.base.SpringIntegrationTest;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class RegisterUserServiceTest extends SpringIntegrationTest {

    private final RegisterUserService registerUserService;

    @Test
    void registerUser() {
        Result<RegisterUserResponse> result = registerUserService.registerUser(
                new UnvalidatedRegisterUserRequest("email@email.com", "", "password"));
        assertTrue(result.isFailure());
        assertEquals("Invalid username: '%s'".formatted(""), result.getError().getMessage());

        UserEntity userEntity = db.findById(UserEntity.class, 2L);
        assertNull(userEntity);
//        assertEquals("email@email.com", userEntity.getEmail());
    }
}