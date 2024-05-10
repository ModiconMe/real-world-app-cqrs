package com.modiconme.realworld.infrastructure.security;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider {

    private final SecurityGateway securityGateway;

    private static AppUserDetails mapToAppUserDetails(ExistedByUsernameUser it) {
        return new AppUserDetails(
                it.getUserId().getValue(),
                it.getEmail().getValue(),
                it.getUsername().getValue(),
                it.getEncodedPassword().getValue()
        );
    }

    private static UsernamePasswordAuthenticationToken mapToUsernamePasswordAuthenticationToken(
            AppUserDetails it
    ) {
        return new UsernamePasswordAuthenticationToken(
                it,
                it.getPassword(),
                it.getAuthorities());
    }

    public Result<Authentication> getAuthentication(String username) {
        return Username.emerge(username)
                .flatMap(securityGateway::findByUsername)
                .map(AuthenticationProvider::mapToAppUserDetails)
                .map(AuthenticationProvider::mapToUsernamePasswordAuthenticationToken);
    }
}
