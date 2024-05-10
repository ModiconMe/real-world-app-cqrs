package com.modiconme.realworld.testcommon;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.valueobjects.EncodedPassword;
import com.modiconme.realworld.domain.common.valueobjects.Password;

public class PlainTextPasswordEncoder implements PasswordEncoder {

    @Override
    public boolean matches(Password password, EncodedPassword encodedPassword) {
        return password.getValue().equals(encodedPassword.getValue());
    }

    @Override
    public String encode(Password password) {
        return password.getValue();
    }

}