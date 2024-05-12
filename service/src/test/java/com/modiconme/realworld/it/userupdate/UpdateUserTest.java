package com.modiconme.realworld.it.userupdate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.userupdate.UpdateUserRequest;
import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;
import com.modiconme.realworld.it.base.SpringIntegrationTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.modiconme.realworld.it.base.TestDataGenerator.uniqEmail;
import static com.modiconme.realworld.it.base.builder.UserEntityTestBuilder.aUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UpdateUserTest extends SpringIntegrationTest {

    final MockMvc mockMvc;
    final ObjectMapper objectMapper;
    final PasswordEncoder passwordEncoder;

    @Test
    void success() throws Exception {
        UserEntity currentUser = db.persisted(aUser(passwordEncoder).bio("bio").build());
        String token = getTokenWithPrefix(authenticator.authenticate(currentUser.getEmail(), "password").token());

        var request = new UpdateUserRequest(uniqEmail(), null, "password1", null, "123");

        mockMvc.perform(put("/api/user")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.user.email").value(request.email()))
                .andExpect(jsonPath("$.data.user.username").value(currentUser.getUsername()))
                .andExpect(jsonPath("$.data.user.token").isNotEmpty())
                .andExpect(jsonPath("$.data.user.bio").doesNotExist())
                .andExpect(jsonPath("$.data.user.image").value(request.image()))
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    void failureWhenEmailAlreadyExists() throws Exception {
        UserEntity currentUser = db.persisted(aUser(passwordEncoder).bio("bio").build());
        UserEntity otherUser = db.persisted(aUser(passwordEncoder).bio("bio").build());
        String token = getTokenWithPrefix(authenticator.authenticate(currentUser.getEmail(), "password").token());

        var request = new UpdateUserRequest(otherUser.getEmail(), null, "password1", null, "123");

        mockMvc.perform(put("/api/user")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Email already exists"));
    }
}
