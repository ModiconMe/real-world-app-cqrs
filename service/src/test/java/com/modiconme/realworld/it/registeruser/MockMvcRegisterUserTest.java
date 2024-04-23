package com.modiconme.realworld.it.registeruser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.domain.registeruser.RegisterUserRequest;
import com.modiconme.realworld.it.base.SpringIntegrationTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class MockMvcRegisterUserTest extends SpringIntegrationTest {

    final MockMvc mockMvc;
    final ObjectMapper objectMapper;

    @Test
    void success() throws Exception {
        var request = new RegisterUserRequest("user@mail.com", "username", "password");

        mockMvc.perform(post("/api/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.user.email").value("user@mail.com"))
                .andExpect(jsonPath("$.data.user.username").value("username"))
                .andExpect(jsonPath("$.data.user.token").isEmpty())
                .andExpect(jsonPath("$.data.user.bio").doesNotExist())
                .andExpect(jsonPath("$.data.user.image").doesNotExist())
                .andExpect(jsonPath("$.error").doesNotExist());

        UserEntity userEntity = db.findById(UserEntity.class, 2L);
        assertNotNull(userEntity);
    }
}
