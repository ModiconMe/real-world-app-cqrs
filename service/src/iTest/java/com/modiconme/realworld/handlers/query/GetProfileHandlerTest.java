package com.modiconme.realworld.handlers.query;

import com.modiconme.realworld.application.query.GetProfileHandler;
import com.modiconme.realworld.core.BaseTest;
import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.ProfileDto;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import com.modiconme.realworld.query.GetProfile;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static com.modiconme.realworld.core.data.var1.UserDataBuilder.aUser;
import static com.modiconme.realworld.core.data.var2.GenericBuilder.uniqString;
import static com.modiconme.realworld.core.data.var2.GenericBuilderDsl.given;
import static com.modiconme.realworld.core.data.var2.GenericBuilderDsl.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
public class GetProfileHandlerTest extends BaseTest {

    private final GetProfileHandler handler;

    @Test
    void shouldGetUnfollowedProfile() {
        // given
        UserEntity currentProfile = given(user(), db);
        UserEntity searchedProfile = given(user(), db);
        GetProfile request = new GetProfile(currentProfile.getUsername(), searchedProfile.getUsername());

        // when
        ProfileDto response = handler.handle(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo(searchedProfile.getUsername());
        assertThat(response.following()).isFalse();
    }

    @Test
    void shouldGetFollowedProfile() {
        // given
        UserEntity currentProfile = given(user());
        UserEntity searchedProfile = given(user());
        FollowRelationEntity followRelation = currentProfile.followUser(searchedProfile);
        db.persisted(followRelation);

        GetProfile request = new GetProfile(currentProfile.getUsername(), searchedProfile.getUsername());

        // when
        ProfileDto response = handler.handle(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo(searchedProfile.getUsername());
        assertThat(response.following()).isTrue();
    }

    @Test
    void shouldGetFollowedProfileVar2() {
        // given
        UserEntity currentProfile = aUser().build();
        UserEntity searchedProfile = aUser().build();
        FollowRelationEntity followRelation = currentProfile.followUser(searchedProfile);

        db.persisted(followRelation);
        GetProfile request = new GetProfile(currentProfile.getUsername(), searchedProfile.getUsername());

        // when
        ProfileDto response = handler.handle(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo(searchedProfile.getUsername());
        assertThat(response.following()).isTrue();
    }

    @Test
    void shouldThrowWhenGetProfileWithWrongUsername() {
        // given
        GetProfile request = new GetProfile(uniqString(8), uniqString(8));

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }
}
