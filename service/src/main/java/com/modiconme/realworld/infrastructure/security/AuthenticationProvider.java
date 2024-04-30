package com.modiconme.realworld.infrastructure.security;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider {

    private final UserRepository userRepository;

    public Result<Authentication> getAuthentication(String username) {
        return userRepository.findByUsername(username)
                .map(AppUserDetails::fromUser)
                .map(userDetails -> new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities())
                );
    }
}
