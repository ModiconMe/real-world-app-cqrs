package com.modiconme.realworld.it.profilefollow;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.profilefollow.FollowProfileResult;
import com.modiconme.realworld.domain.profilefollow.FollowedProfileDto;
import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;
import com.modiconme.realworld.infrastructure.web.controller.RestResponse;
import com.modiconme.realworld.it.base.SpringIntegrationTest;
import com.modiconme.realworld.it.base.assertions.RestResponseAssertion;
import com.modiconme.realworld.it.base.extension.Auth;
import com.modiconme.realworld.it.base.extension.AuthExtension;
import com.modiconme.realworld.it.base.extension.ContextHolderExtension;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.modiconme.realworld.it.base.builder.UserEntityTestBuilder.aUser;

@ExtendWith({ContextHolderExtension.class, AuthExtension.class})
@RequiredArgsConstructor
class FollowUserTest extends SpringIntegrationTest {

    final PasswordEncoder passwordEncoder;
    final TestRestTemplate testRestTemplate;

    @Auth
    @Test
    void success() {
        UserEntity currentUser = db.persisted(aUser(passwordEncoder).build());
        UserEntity profile = db.persisted(aUser(passwordEncoder).build());
        String token = authClient.authenticate(currentUser.getEmail(), "password").token();

        ResponseEntity<RestResponse<FollowProfileResult>> result = testRestTemplate.exchange(
                "/api/profiles/follow/{profileUsername}",
                HttpMethod.POST,
                new HttpEntity<>(getAuthorizationHeader(token)),
                new ParameterizedTypeReference<>() {
                }, profile.getUsername());

        var expectedData = new FollowProfileResult(
                new FollowedProfileDto(profile.getUsername(), null, null, true));

        RestResponseAssertion.assertThat(result)
                .hasStatusCodeEquals(HttpStatus.OK)
                .hasDataEquals(expectedData)
                .hasErrorEquals(null);
    }

}
