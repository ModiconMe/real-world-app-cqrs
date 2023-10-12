package com.modiconme.realworld.handlers.command;

import com.modiconme.realworld.application.command.LoginUserHandler;
import com.modiconme.realworld.command.LoginUser;
import com.modiconme.realworld.core.BaseTest;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.UserDto;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.JwtUtils;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.modiconme.realworld.core.data.var2.GenericBuilder.NOT_EXIST;
import static com.modiconme.realworld.core.data.var2.GenericBuilderDsl.given;
import static com.modiconme.realworld.core.data.var2.GenericBuilderDsl.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
public class UserLoginHandlerTest extends BaseTest {

    private final LoginUserHandler handler;

    @Test
    void shouldLoginUser(@Autowired JwtUtils jwtUtils, @Autowired PasswordEncoder passwordEncoder) {
        // given
        UserEntity user = given(user().password(passwordEncoder.encode("password")));
        db.persisted(user);
        LoginUser request = new LoginUser(user.getEmail(), "password");

        // when
        UserDto response = handler.handle(request).getUser();

        String token = jwtUtils.generateAccessToken(AppUserDetails.fromUser(user));

        // then
        assertThat(response.username()).isEqualTo(user.getUsername());
        assertThat(response.email()).isEqualTo(request.getEmail());
        assertThat(response.token()).isEqualTo(token);
        assertThat(response.bio()).isEqualTo(user.getBio());
        assertThat(response.image()).isEqualTo(user.getImage());
    }

    @Test
    void shouldThrow_whenLoginUser_withWrongEmail() {
        // given
        LoginUser request = new LoginUser(NOT_EXIST, NOT_EXIST);

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void shouldThrow_whenLoginUser_withWrongPassword() {
        // given
        LoginUser request = new LoginUser(NOT_EXIST, NOT_EXIST);

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("User not found");
    }
}
