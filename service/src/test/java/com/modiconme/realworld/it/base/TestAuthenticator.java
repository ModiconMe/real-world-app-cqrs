package com.modiconme.realworld.it.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modiconme.realworld.domain.loginuser.LoginUserRequest;
import com.modiconme.realworld.domain.loginuser.LoginUserResponse;
import com.modiconme.realworld.infrastructure.security.jwt.JwtConfig;
import com.modiconme.realworld.infrastructure.web.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@RequiredArgsConstructor
public class TestAuthenticator {

    final ObjectMapper objectMapper;
    final JwtConfig jwtConfig;

    public String authenticate(String email, String password, MockMvc mockMvc) throws Exception {
        var loginRequest = new LoginUserRequest(email, password);

        String json = mockMvc.perform(post("/api/users/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        RestResponse<LoginUserResponse> loginUserResponse = objectMapper.readValue(json, new TypeReference<>() {
        });

        return jwtConfig.getTokenPrefix() + " " + loginUserResponse.getData().user().token();
    }
}
