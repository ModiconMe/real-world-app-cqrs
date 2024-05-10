package com.modiconme.realworld.domain.userlogin;

import com.modiconme.realworld.domain.common.valueobjects.Bio;
import com.modiconme.realworld.domain.common.valueobjects.Email;
import com.modiconme.realworld.domain.common.valueobjects.Image;
import com.modiconme.realworld.domain.common.valueobjects.Username;

public record LoginUserDto(String email, String token, String username, String bio, String image) {

    LoginUserDto(Email email, String token, Username username, Bio bio, Image image) {
        this(email.getValue(), token, username.getValue(), bio.getValue().orElse(null),
                image.getValue().orElse(null));
    }
}


