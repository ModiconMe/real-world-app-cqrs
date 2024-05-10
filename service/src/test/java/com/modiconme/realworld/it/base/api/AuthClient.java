package com.modiconme.realworld.it.base.api;

import com.modiconme.realworld.domain.userlogin.LoginUserDto;
import com.modiconme.realworld.domain.userlogin.LoginUserRequest;
import com.modiconme.realworld.domain.userlogin.LoginUserResponse;
import com.modiconme.realworld.domain.userregister.RegisterUserRequest;
import com.modiconme.realworld.domain.userregister.RegisterUserResponse;
import com.modiconme.realworld.domain.userregister.RegisteredUserDto;
import com.modiconme.realworld.infrastructure.security.jwt.JwtConfig;
import com.modiconme.realworld.infrastructure.web.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.modiconme.realworld.it.base.TestDataGenerator.uniqEmail;
import static com.modiconme.realworld.it.base.TestDataGenerator.uniqString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
@RequiredArgsConstructor
public class AuthClient {

    final JwtConfig jwtConfig;
    final TestRestTemplate testRestTemplate;

    public LoginUserDto authenticate(String email, String password) {
        var request = new LoginUserRequest(email, password);

        ResponseEntity<RestResponse<LoginUserResponse>> response = testRestTemplate.exchange(
                "/api/users/login",
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                });

        assertNotNull(response.getBody());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        return response.getBody().getData().user();
    }

    public RegisteredUserDto register() {
        var request = new RegisterUserRequest(uniqEmail(), uniqString(), "password");

        ResponseEntity<RestResponse<RegisterUserResponse>> response = testRestTemplate.exchange(
                "/api/users",
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                });

        assertNotNull(response.getBody());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        return response.getBody().getData().user();
    }
}
