package com.modiconme.realworld.handlers.command;

import com.modiconme.realworld.application.command.UpdateUserHandler;
import com.modiconme.realworld.command.UpdateUser;
import com.modiconme.realworld.core.BaseTest;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.UserDto;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.JwtUtils;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.modiconme.realworld.core.data.var2.GenericBuilder.*;
import static com.modiconme.realworld.core.data.var2.GenericBuilderDsl.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
public class UserUpdateHandlerTest extends BaseTest {

    private final UpdateUserHandler handler;

    @Test
    void shouldUpdateUser(@Autowired JwtUtils jwtUtils) {
        // given
        UserEntity user = db.persisted(given(user()));
        UpdateUser request = given(updateUser(user)
                .withNewEmail(uniqEmail())
                .withNewUsername(uniqString())
                .withNewPassword(uniqString())
                .withNewBio(uniqString())
                .withNewImage(uniqString()));

        // when
        UserDto response = handler.handle(request).getUser();

        String token = jwtUtils.generateAccessToken(AppUserDetails.fromUser(user));

        // then
        assertThat(response.username()).isEqualTo(request.getUsername());
        assertThat(response.email()).isEqualTo(request.getEmail());
        assertThat(response.token()).isNotEqualTo(token);
        assertThat(response.bio()).isEqualTo(request.getBio());
        assertThat(response.image()).isEqualTo(request.getImage());
    }

    @Test
    void shouldUpdateUser_withOldAndNullAndEmptyValProvided(@Autowired JwtUtils jwtUtils) {
        // given
        UserEntity user = db.persisted(given(user()));

        UpdateUser request = given(updateUser(user)
                .withNewBio(uniqString())
                .withNewUsername(SPACE_STRING)
                .withNewPassword(EMPTY_STRING));

        // when
        UserDto response = handler.handle(request).getUser();

        String token = jwtUtils.generateAccessToken(AppUserDetails.fromUser(user));

        // then
        assertThat(response.username()).isEqualTo(user.getUsername());
        assertThat(response.email()).isEqualTo(user.getEmail());
        assertThat(response.token()).isEqualTo(token);
        assertThat(response.bio()).isEqualTo(request.getBio());
        assertThat(response.image()).isEqualTo(request.getImage());
    }

    @Test
    void shouldThrow_whenUpdateUser_withExistsEmail() {
        // given
        UserEntity user1 = db.persisted(given(user()));
        UserEntity user2 = db.persisted(given(user()));

        UpdateUser request = given(updateUser(user1).withNewEmail(user2.getEmail()));

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("User is already exists");
    }

    @Test
    void shouldThrow_whenUpdateUser_withExistsUsername() {
        // given
        UserEntity user1 = db.persisted(given(user()));
        UserEntity user2 = db.persisted(given(user()));

        UpdateUser request = given(updateUser(user1).withNewUsername(user2.getUsername()));

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("User is already exists");
    }
}
