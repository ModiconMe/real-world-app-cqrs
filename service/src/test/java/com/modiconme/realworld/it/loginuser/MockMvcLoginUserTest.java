package com.modiconme.realworld.it.loginuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modiconme.realworld.domain.common.Password;
import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.domain.loginuser.LoginUserRequest;
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
class MockMvcLoginUserTest extends SpringIntegrationTest {

    final MockMvc mockMvc;
    final ObjectMapper objectMapper;
    final PasswordEncoder passwordEncoder;

    @Test
    void success() throws Exception {
        UserEntity user = UserEntity.builder()
                .email(uniqEmail())
                .password(Password.emerge("password")
                        .flatMap(it -> it.encode(passwordEncoder)).getData().getValue())
                .username(uniqString())
                .build();
        db.persisted(user);

        var request = new LoginUserRequest(user.getEmail(), "password");

        mockMvc.perform(post("/api/users/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.data.user.username").value(user.getUsername()))
                .andExpect(jsonPath("$.data.user.token").isEmpty())
                .andExpect(jsonPath("$.data.user.bio").doesNotExist())
                .andExpect(jsonPath("$.data.user.image").doesNotExist())
                .andExpect(jsonPath("$.error").doesNotExist());
    }
}
