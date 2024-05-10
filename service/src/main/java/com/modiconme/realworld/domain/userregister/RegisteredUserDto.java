package com.modiconme.realworld.domain.userregister;

import com.modiconme.realworld.domain.common.valueobjects.Email;
import com.modiconme.realworld.domain.common.valueobjects.Username;

public record RegisteredUserDto(String email, String token, String username, String bio, String image) {

    RegisteredUserDto(Email email, String token, Username username) {
        this(email.getValue(), token, username.getValue(), null, null);
    }
}


