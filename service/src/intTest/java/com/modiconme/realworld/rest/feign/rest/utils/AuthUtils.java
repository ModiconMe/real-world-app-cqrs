package com.modiconme.realworld.rest.feign.rest.utils;

import com.modiconme.realworld.command.LoginUser;
import com.modiconme.realworld.command.LoginUserResult;
import com.modiconme.realworld.command.RegisterUser;
import com.modiconme.realworld.rest.feign.client.UserClient;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AuthUtils {

    private final UserClient userClient;

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

    public void login(String email, String password) {
        LoginUserResult result = userClient.login(new LoginUser(email, password));
        TokenHolder.setToken(result.getUser().token());
    }

    public void logout() {
        TokenHolder.clear();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TokenHolder {
        private static String token;

        public static String getToken() {
            return token;
        }

        public static void setToken(String token) {
            TokenHolder.token = token;
        }

        public static void clear() {
            TokenHolder.token = null;
        }
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

