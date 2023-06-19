package com.modiconme.realworld.handlers;

import com.modiconme.realworld.application.query.GetProfileHandler;
import com.modiconme.realworld.core.BaseTest;
import com.modiconme.realworld.core.TestDbFacade;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.ProfileDto;
import com.modiconme.realworld.query.GetProfile;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static com.modiconme.realworld.core.UserDataBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GetProfileHandlerTest extends BaseTest {

    private final GetProfileHandler handler;
    private final TestDbFacade db;

    @Test
    void shouldGetProfile() {
        // given
        UserEntity currentProfile = aUser().withUsername("currentUsername").build();
        UserEntity searchedProfile = aUser().withUsername("testUsername").build();
//        db.persisted(searchedProfile);
//        db.persisted(currentProfile);
        db.persisted(searchedProfile, currentProfile);
        GetProfile request = new GetProfile(currentProfile.getUsername(), searchedProfile.getUsername());

        // when
        ProfileDto response = handler.handle(request).getProfile();

        // then
        assertThat(response.username()).isEqualTo("testUsername");
        assertThat(response.following()).isEqualTo(false);
    }
}
