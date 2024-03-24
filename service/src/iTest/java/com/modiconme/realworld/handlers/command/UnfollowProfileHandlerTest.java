package com.modiconme.realworld.handlers.command;

import com.modiconme.realworld.application.command.UnfollowProfileHandler;
import com.modiconme.realworld.command.UnfollowProfile;
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
public class UnfollowProfileHandlerTest extends BaseTest {

    private final UnfollowProfileHandler handler;

    @Test
    void shouldUnfollowProfile() {
        // given
        UserEntity currentUser = given(user());
        UserEntity followee = given(user());
        db.persisted(currentUser.followUser(followee));
        var request = new UnfollowProfile(currentUser.getUsername(), followee.getUsername());

        // when
        ProfileDto response = handler.handle(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo(followee.getUsername());
        assertThat(response.bio()).isEqualTo(followee.getBio());
        assertThat(response.image()).isEqualTo(followee.getImage());
        assertThat(response.following()).isEqualTo(false);
    }

    @Test
    void shouldThrow_whenUnfollowProfile_withWrongUsername() {
        // given
        UserEntity currentUser = given(user());
        var request = new UnfollowProfile(currentUser.getUsername(), NOT_EXIST);

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }
}
