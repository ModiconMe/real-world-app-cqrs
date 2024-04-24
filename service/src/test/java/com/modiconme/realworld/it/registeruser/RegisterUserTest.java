package com.modiconme.realworld.it.registeruser;

import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.domain.registeruser.RegisterUserRequest;
import com.modiconme.realworld.domain.registeruser.RegisterUserResponse;
import com.modiconme.realworld.dto.UserDto;
import com.modiconme.realworld.infrastructure.web.controller.RestResponse;
import com.modiconme.realworld.it.base.SpringIntegrationTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class RegisterUserTest extends SpringIntegrationTest {

    private final TestRestTemplate testRestTemplate;

    @Test
    @Disabled("TestRestTemplate")
    void success() {
        var request = new HttpEntity<>(new RegisterUserRequest("user@mail.com", "username", "password"));
        ResponseEntity<RestResponse<RegisterUserResponse>> response = testRestTemplate.exchange(
                "/api/users", HttpMethod.POST, request, new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserDto user = response.getBody().getData().user();
        assertEquals("user@mail.com", user.email());
        assertEquals("username", user.username());
        assertNull(user.bio());
        assertNull(user.image());

        UserEntity userEntity = db.findById(UserEntity.class, 2L);
        assertNotNull(userEntity);
    }
}
