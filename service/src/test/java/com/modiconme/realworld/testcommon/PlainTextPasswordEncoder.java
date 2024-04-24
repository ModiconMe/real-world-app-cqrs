package com.modiconme.realworld.testcommon;

import com.modiconme.realworld.domain.common.Password;
import com.modiconme.realworld.domain.common.PasswordEncoder;

public class PlainTextPasswordEncoder implements PasswordEncoder {

    @Override
    public boolean matches(Password password, String hash) {
        return password.getValue().equals(hash);
    }

    @Override
    public Password encode(Password password) {
        return password;
    }

}