package com.modiconme.realworld.handlers.command;

import com.modiconme.realworld.application.command.FollowProfileHandler;
import com.modiconme.realworld.command.FollowProfile;
import com.modiconme.realworld.core.BaseTest;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.ProfileDto;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static com.modiconme.realworld.core.data.var2.GenericBuilder.NOT_EXIST;
import static com.modiconme.realworld.core.data.var2.GenericBuilderDsl.given;
import static com.modiconme.realworld.core.data.var2.GenericBuilderDsl.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
public class FollowProfileHandlerTest extends BaseTest {

    private final FollowProfileHandler handler;

    @Test
    void shouldFollowProfile() {
        // given
        UserEntity currentUser = db.persisted(given(user()));
        UserEntity followee = db.persisted(given(user()));

        var request = new FollowProfile(currentUser.getUsername(), followee.getUsername());

        // when
        ProfileDto response = handler.handle(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo(followee.getUsername());
        assertThat(response.bio()).isEqualTo(followee.getBio());
        assertThat(response.image()).isEqualTo(followee.getImage());
        assertThat(response.following()).isEqualTo(true);
    }

    @Test
    void shouldThrow_whenFollowProfile_withWrongUsername() {
        // given
        UserEntity currentUser = db.persisted(given(user()));
        var request = new FollowProfile(currentUser.getUsername(), NOT_EXIST);

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }
}
