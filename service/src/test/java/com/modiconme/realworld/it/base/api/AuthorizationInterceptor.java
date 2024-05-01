package com.modiconme.realworld.it.base.api;

import com.modiconme.realworld.dto.UserDto;
import com.modiconme.realworld.infrastructure.security.jwt.JwtConfig;
import com.modiconme.realworld.it.base.extension.AuthExtension;
import com.modiconme.realworld.it.base.extension.ContextHolderExtension;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements ClientHttpRequestInterceptor {

    final JwtConfig jwtConfig;

    @Override
    public @NotNull ClientHttpResponse intercept(@NotNull HttpRequest request, byte @NotNull [] body,
                                                 @NotNull ClientHttpRequestExecution execution) throws IOException {
        UserDto user = AuthExtension.getUser(ContextHolderExtension.Holder.INSTANCE.get());
        if (user != null) {
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, jwtConfig.getTokenPrefix() + " " + user.token());
        }
        return execution.execute(request, body);
    }
}
