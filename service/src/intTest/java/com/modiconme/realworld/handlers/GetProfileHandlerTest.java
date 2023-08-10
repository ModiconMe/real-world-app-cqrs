package com.modiconme.realworld.handlers;

import com.modiconme.realworld.application.query.GetProfileHandler;
import com.modiconme.realworld.core.BaseTest;
import com.modiconme.realworld.core.TestDbFacade;
import com.modiconme.realworld.core.builders.FollowRelationDataBuilder;
import com.modiconme.realworld.core.builders.UserDataBuilder;
import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.FollowRelationId;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.ProfileDto;
import com.modiconme.realworld.infrastructure.db.jpa.DataFollowRelationRepository;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import com.modiconme.realworld.query.GetProfile;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
public class GetProfileHandlerTest extends BaseTest {

    private final GetProfileHandler handler;
    private final TestDbFacade db;

    private final DataFollowRelationRepository repository;

    @Test
    void shouldGetUnfollowedProfile() {
        // given
        UserEntity currentProfile = saveRandomUser();
        UserEntity searchedProfile = saveRandomUser();
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
        UserEntity currentProfile = UserDataBuilder.aUser().withUsername(FAKER.name().username()).build();
        UserEntity searchedProfile = UserDataBuilder.aUser().withUsername(FAKER.name().username()).build();

        FollowRelationEntity followRelation = FollowRelationDataBuilder.aFollowRelation()
                .withId(new FollowRelationId(searchedProfile.getId(), currentProfile.getId()))
                .withFollowee(searchedProfile)
                .withFollower(currentProfile)
                .build();

//        db.merge(searchedProfile);
//        db.merge(currentProfile);
//
        db.persisted(followRelation);

//        repository.save(followRelation);

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
        GetProfile request = new GetProfile(FAKER.name().username(), FAKER.name().username());

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }

    private UserEntity saveRandomUser() {
        var user = UserDataBuilder.aUser().withUsername(FAKER.name().username()).build();
        return db.persisted(user);
    }

    private FollowRelationEntity follow(UserEntity followee, UserEntity follower) {
        FollowRelationEntity followRelation = FollowRelationDataBuilder.aFollowRelation()
                .withId(new FollowRelationId(followee.getId(), follower.getId()))
                .withFollowee(followee)
                .withFollower(follower)
                .build();
        return db.persisted(followRelation);
    }
}
