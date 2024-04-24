package com.modiconme.realworld.domain.common;

public interface PasswordEncoder {
    boolean matches(Password password, String hash);

    Password encode(Password password);
}
