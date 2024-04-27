package com.modiconme.realworld.infrastructure.security;

import com.modiconme.realworld.domain.common.Password;
import com.modiconme.realworld.domain.common.PasswordEncoder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BCryptPasswordEncoder implements PasswordEncoder {

    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder =
            new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

    @Override
    public boolean matches(Password password, String hash) {
        return passwordEncoder.matches(password.getValue(), hash);
    }

    @Override
    public String encode(Password password) {
        return passwordEncoder.encode(password.getValue());
    }
}
