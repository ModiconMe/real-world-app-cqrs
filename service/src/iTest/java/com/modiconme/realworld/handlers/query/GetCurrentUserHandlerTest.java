package com.modiconme.realworld.handlers.query;

import com.modiconme.realworld.application.query.GetCurrentUserHandler;
import com.modiconme.realworld.core.BaseTest;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.UserDto;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.JwtUtils;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import com.modiconme.realworld.query.GetCurrentUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.modiconme.realworld.core.data.var2.GenericBuilder.NOT_EXIST;
import static com.modiconme.realworld.core.data.var2.GenericBuilderDsl.given;
import static com.modiconme.realworld.core.data.var2.GenericBuilderDsl.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GetCurrentUserHandlerTest extends BaseTest {

    private final GetCurrentUserHandler handler;

    public GetCurrentUserHandlerTest(GetCurrentUserHandler handler) {
        this.handler = handler;
    }

    @Test
    void shouldGetCurrentUser(@Autowired JwtUtils jwtUtils) {
        // given
        UserEntity user = db.persisted(given(user()));
        var request = new GetCurrentUser(user.getUsername());

        // when
        UserDto response = handler.handle(request).getUser();

        String token = jwtUtils.generateAccessToken(AppUserDetails.fromUser(user));

        // then
        assertThat(response.username()).isEqualTo(user.getUsername());
        assertThat(response.email()).isEqualTo(user.getEmail());
        assertThat(response.token()).isEqualTo(token);
        assertThat(response.bio()).isEqualTo(user.getBio());
        assertThat(response.image()).isEqualTo(user.getImage());
    }

    @Test
    void shouldThrow_whenGetCurrent_withWrongEmail() {
        // given
        var request = new GetCurrentUser(NOT_EXIST);

        // when
        // then
        assertThatThrownBy(() -> handler.handle(request))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Profile not found");
    }

}
