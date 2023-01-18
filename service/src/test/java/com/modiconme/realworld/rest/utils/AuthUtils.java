package com.modiconme.realworld.rest.utils;

import com.modiconme.realworld.command.LoginUser;
import com.modiconme.realworld.command.LoginUserResult;
import com.modiconme.realworld.command.RegisterUser;
import com.modiconme.realworld.client.UserClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthUtils {

    @Autowired(required = false)
    private UserClient userClient;

    public RegisteredUser register() {
        String uuid = UUID.randomUUID().toString();
        return register(uuid, email(uuid), uuid);
    }

    public RegisteredUser register(String username, String email, String password) {
        userClient.register(RegisterUser.builder()
                .username(username)
                .email(email)
                .password(password)
                .build());
        return new RegisteredUser(email, username, password);
    }

    private String email(String uuid) {
        return uuid + "@ex.com";
    }

    public void login(String cred) {
        login(email(cred), cred);
    }

    public void login(String email, String password) {
        LoginUserResult result = userClient.login(new LoginUser(email, password));
        TokenHolder.token = result.getUser().token();
    }

    public void logout() {
        TokenHolder.token = null;
    }

    public static class TokenHolder {
        public static String token;
    }

    @AllArgsConstructor
    @Getter
    public class RegisteredUser {
        private String email;
        private String username;
        private String password;

        public RegisteredUser login() {
            AuthUtils.this.login(email, password);
            return this;
        }
    }

}
