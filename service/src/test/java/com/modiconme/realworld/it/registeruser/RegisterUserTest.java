package com.modiconme.realworld.it.registeruser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modiconme.realworld.domain.registeruser.RegisterUserRequest;
import com.modiconme.realworld.it.base.SpringIntegrationTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.modiconme.realworld.it.base.TestDataGenerator.uniqEmail;
import static com.modiconme.realworld.it.base.TestDataGenerator.uniqString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class RegisterUserTest extends SpringIntegrationTest {

    final MockMvc mockMvc;
    final ObjectMapper objectMapper;

    @Test
    void success() throws Exception {
        var request = new RegisterUserRequest(uniqEmail(), uniqString(), "password");

        mockMvc.perform(post("/api/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.user.email").value(request.email()))
                .andExpect(jsonPath("$.data.user.username").value(request.username()))
                .andExpect(jsonPath("$.data.user.token").isNotEmpty())
                .andExpect(jsonPath("$.data.user.bio").doesNotExist())
                .andExpect(jsonPath("$.data.user.image").doesNotExist())
                .andExpect(jsonPath("$.error").doesNotExist());
    }
}
