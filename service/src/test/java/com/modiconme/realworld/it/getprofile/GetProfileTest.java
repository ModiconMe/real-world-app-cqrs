package com.modiconme.realworld.it.getprofile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.it.base.SpringIntegrationTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.modiconme.realworld.it.base.builder.FollowRelationTestBuilder.aFollowRelation;
import static com.modiconme.realworld.it.base.builder.UserEntityTestBuilder.aUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class GetProfileTest extends SpringIntegrationTest {

    final MockMvc mockMvc;
    final ObjectMapper objectMapper;
    final PasswordEncoder passwordEncoder;

    @Test
    void successFollow() throws Exception {
        UserEntity user1 = db.persisted(aUser(passwordEncoder).build());
        UserEntity user2 = db.persisted(aUser(passwordEncoder).build());
        db.persisted(aFollowRelation().followee(user2).follower(user1).build());

        String token = authenticator.authenticate(user1.getEmail(), "password", mockMvc);
        mockMvc.perform(post("/api/profiles/{profileUsername}", user2.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.profile.username").value(user2.getUsername()))
                .andExpect(jsonPath("$.data.profile.bio").doesNotExist())
                .andExpect(jsonPath("$.data.profile.image").doesNotExist())
                .andExpect(jsonPath("$.data.profile.following").value(true))
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    void successUnfollow() throws Exception {
        UserEntity user1 = db.persisted(aUser(passwordEncoder).build());
        UserEntity user2 = db.persisted(aUser(passwordEncoder).build());

        String token = authenticator.authenticate(user1.getEmail(), "password", mockMvc);
        mockMvc.perform(post("/api/profiles/{profileUsername}", user2.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.profile.username").value(user2.getUsername()))
                .andExpect(jsonPath("$.data.profile.bio").doesNotExist())
                .andExpect(jsonPath("$.data.profile.image").doesNotExist())
                .andExpect(jsonPath("$.data.profile.following").value(false))
                .andExpect(jsonPath("$.error").doesNotExist());
    }
}
