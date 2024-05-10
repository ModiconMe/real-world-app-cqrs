package com.modiconme.realworld.domain.common;

import com.modiconme.realworld.domain.common.valueobjects.EncodedPassword;
import com.modiconme.realworld.domain.common.valueobjects.Password;

public interface PasswordEncoder {
    boolean matches(Password password, EncodedPassword hash);

    String encode(Password password);
}
