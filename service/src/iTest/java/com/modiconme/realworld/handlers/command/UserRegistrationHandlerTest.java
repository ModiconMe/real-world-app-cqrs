package com.modiconme.realworld.handlers.command;

import com.modiconme.realworld.application.command.RegisterUserHandler;
import com.modiconme.realworld.command.RegisterUser;
import com.modiconme.realworld.core.BaseTest;
import com.modiconme.realworld.dto.UserDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import static com.modiconme.realworld.core.data.var2.GenericBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
public class UserRegistrationHandlerTest extends BaseTest {

    private final RegisterUserHandler handler;

    @Test
    void shouldRegisterUser() {
        // given
        var request = new RegisterUser(uniqEmail(), uniqString(), uniqString());

        // when
        UserDto response = handler.handle(request).getUser();

        // then
        assertThat(response.username()).isEqualTo(request.getUsername());
        assertThat(response.email()).isEqualTo(request.getEmail());
        assertThat(response.token()).isNotNull();
        assertThat(response.bio()).isNull();
        assertThat(response.image()).isNull();
    }

    @Test
    void shouldThrow_whenRegisterInvalidUser() {
        // given
        var nullEmail = new RegisterUser(null, uniqString(), uniqString());
        var emptyEmail = new RegisterUser(EMPTY_STRING, uniqString(), uniqString());
        var nullUsername = new RegisterUser(uniqEmail(), null, uniqString());
        var emptyUsername = new RegisterUser(uniqEmail(), EMPTY_STRING, uniqString());
        var nullPassword = new RegisterUser(uniqEmail(), uniqString(), null);
        var emptyPassword = new RegisterUser(uniqEmail(), uniqString(), EMPTY_STRING);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // when
        // then
        assertThat(validator.validate(nullEmail)).hasSize(1);
        assertThat(validator.validate(emptyEmail)).hasSize(2);
        assertThat(validator.validate(nullUsername)).hasSize(1);
        assertThat(validator.validate(emptyUsername)).hasSize(2);
        assertThat(validator.validate(nullPassword)).hasSize(1);
        assertThat(validator.validate(emptyPassword)).hasSize(2);

        assertThatThrownBy(() -> handler.handle(nullEmail)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> handler.handle(emptyEmail)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> handler.handle(nullUsername)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> handler.handle(emptyUsername)).isInstanceOf(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> handler.handle(nullPassword)).isInstanceOf(IllegalArgumentException.class);
    }
}
