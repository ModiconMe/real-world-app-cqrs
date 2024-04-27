package com.modiconme.realworld.it.follow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modiconme.realworld.domain.common.Password;
import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.domain.followprofile.FollowProfileRequest;
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
public class FollowUserTest extends SpringIntegrationTest {

    final MockMvc mockMvc;
    final ObjectMapper objectMapper;
    final PasswordEncoder passwordEncoder;

    @Test
    void success() throws Exception {
        UserEntity user1 = UserEntity.builder()
                .email(uniqEmail())
                .password(Password.emerge("password")
                        .flatMap(it -> it.encode(passwordEncoder)).getData().getValue())
                .username(uniqString())
                .build();
        db.persisted(user1);
        UserEntity user2 = UserEntity.builder()
                .email(uniqEmail())
                .password(Password.emerge("password")
                        .flatMap(it -> it.encode(passwordEncoder)).getData().getValue())
                .username(uniqString())
                .build();
        db.persisted(user2);

        var request = new FollowProfileRequest(user1.getUsername(), user2.getUsername());

        mockMvc.perform(post("/api/profiles/follow")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.profile.username").value(user2.getUsername()))
                .andExpect(jsonPath("$.data.profile.bio").doesNotExist())
                .andExpect(jsonPath("$.data.profile.image").doesNotExist())
                .andExpect(jsonPath("$.data.profile.following").isBoolean())
                .andExpect(jsonPath("$.error").doesNotExist());
    }

}
