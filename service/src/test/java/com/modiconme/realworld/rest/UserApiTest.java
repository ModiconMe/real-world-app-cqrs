package com.modiconme.realworld.rest;

import com.modiconme.realworld.client.UserClient;
import com.modiconme.realworld.command.LoginUser;
import com.modiconme.realworld.command.RegisterUser;
import com.modiconme.realworld.command.UpdateUser;
import com.modiconme.realworld.dto.UserDto;
import com.modiconme.realworld.rest.utils.AuthUtils;
import com.modiconme.realworld.rest.config.FeignBasedRestTest;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class UserApiTest extends FeignBasedRestTest {

    public static final String ALTERED_EMAIL = "altered-email@example.com";
    public static final String ALTERED_USERNAME = "altered-username";
    public static final String ALTERED_PASSWORD = "altered-password";
    public static final String ALTERED_BIO = "altered-bio";
    public static final String ALTERED_IMAGE = "altered-image";

    @Autowired
    private AuthUtils auth;

    @Autowired
    private UserClient userClient;

    @Test
    void should_returnCorrectData_whenRegisterUser() {
        RegisterUser command = registerCommand();

        UserDto user = userClient.register(command).getUser();

        assertThat(user.username()).isEqualTo(command.getUsername());
        assertThat(user.email()).isEqualTo(command.getEmail());
        assertThat(user.token()).isNotEmpty();
    }

    @Test
    void should_returnCorrectData_whenLoginUser() {
        RegisterUser command = registerCommand();

        userClient.register(command).getUser();
        UserDto user = userClient.login(new LoginUser(command.getEmail(), command.getPassword())).getUser();

        assertThat(user.username()).isEqualTo(command.getUsername());
        assertThat(user.email()).isEqualTo(command.getEmail());
        assertThat(user.token()).isNotEmpty();
    }

    @Test
    void should_throw404_whenLoginUserWithWrongEmail() {
        RegisterUser command = registerCommand();

        userClient.register(command);
        FeignException exception = catchThrowableOfType(() -> userClient.login(new LoginUser(UUID.randomUUID().toString() + "@gmail.com", UUID.randomUUID().toString())),
                FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_throw401_whenLoginUserWithWrongPassword() {
        RegisterUser command = registerCommand();

        userClient.register(command);
        FeignException exception = catchThrowableOfType(() -> userClient.login(new LoginUser(command.getEmail(), UUID.randomUUID().toString())),
                FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void should_returnCorrectData_whenGetUserWithAuth() {
        AuthUtils.RegisteredUser login = auth.register().login();

        UserDto user = userClient.getUser().getUser();

        assertThat(user.username()).isEqualTo(login.getUsername());
        assertThat(user.email()).isEqualTo(login.getEmail());
        assertThat(user.token()).isNotEmpty();
    }

    @Test
    void should_returnCorrectData_whenUpdateUser() {
        auth.register().login();

        UpdateUser newUser = UpdateUser.builder()
                .username(ALTERED_USERNAME)
                .email(ALTERED_EMAIL)
                .password(ALTERED_PASSWORD)
                .bio(ALTERED_BIO)
                .image(ALTERED_IMAGE)
                .build();
        UserDto user = userClient.updateUser(newUser).getUser();

        assertThat(user.username()).isEqualTo(newUser.getUsername());
        assertThat(user.email()).isEqualTo(newUser.getEmail());
        assertThat(user.token()).isNotEmpty();
    }

    @Test
    void should_throw422_whenUpdateUserWithExistingEmail() {
        AuthUtils.RegisteredUser register = auth.register();

        auth.register().login();

        UpdateUser newUser = UpdateUser.builder()
                .email(register.getEmail())
                .build();
        FeignException exception = catchThrowableOfType(() -> userClient.updateUser(newUser).getUser(), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    void should_throw422_whenUpdateUserWithExistingUsername() {
        AuthUtils.RegisteredUser register = auth.register();

        auth.register().login();

        UpdateUser newUser = UpdateUser.builder()
                .username(register.getUsername())
                .build();
        FeignException exception = catchThrowableOfType(() -> userClient.updateUser(newUser).getUser(), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    private static RegisterUser registerCommand() {
        return RegisterUser.builder()
                .username(UUID.randomUUID().toString())
                .email(UUID.randomUUID() + "@ex.com")
                .password(UUID.randomUUID().toString())
                .build();
    }

}
