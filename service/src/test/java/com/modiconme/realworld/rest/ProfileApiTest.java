package com.modiconme.realworld.rest;

import com.modiconme.realworld.client.ProfileClient;
import com.modiconme.realworld.dto.ProfileDto;
import com.modiconme.realworld.rest.config.FeignBasedRestTest;
import com.modiconme.realworld.rest.utils.AuthUtils;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class ProfileApiTest extends FeignBasedRestTest {

    @Autowired
    private AuthUtils auth;

    @Autowired
    private ProfileClient profileClient;

    @Test
    void should_returnCorrectData_whenGetProfile() {
        AuthUtils.RegisteredUser profile1 = auth.register();

        auth.register().login();

        ProfileDto profile = profileClient.getProfile(profile1.getUsername()).getProfile();

        assertThat(profile.username()).isEqualTo(profile1.getUsername());
        assertThat(profile.following()).isFalse();
    }

    @Test
    void should_throw404_whenProfileIsNotExist() {
        auth.register().login();

        FeignException exception = catchThrowableOfType(() -> profileClient.getProfile(UUID.randomUUID().toString()), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_returnCorrectData_whenFollowProfile() {
        AuthUtils.RegisteredUser profile1 = auth.register();

        auth.register().login();

        ProfileDto profile = profileClient.followProfile(profile1.getUsername()).getProfile();

        assertThat(profile.username()).isEqualTo(profile1.getUsername());
        assertThat(profile.following()).isTrue();
    }

    @Test
    void should_returnCorrectData_whenUnfollowProfile() {
        AuthUtils.RegisteredUser profile1 = auth.register();

        auth.register().login();

        // follow
        ProfileDto profile = profileClient.followProfile(profile1.getUsername()).getProfile();
        assertThat(profile.username()).isEqualTo(profile1.getUsername());
        assertThat(profile.following()).isTrue();

        // unfollow
        profile = profileClient.unfollowProfile(profile1.getUsername()).getProfile();
        assertThat(profile.username()).isEqualTo(profile1.getUsername());
        assertThat(profile.following()).isFalse();
    }

}
