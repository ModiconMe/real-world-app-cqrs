package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.valueobjects.Bio;
import com.modiconme.realworld.domain.common.valueobjects.Email;
import com.modiconme.realworld.domain.common.valueobjects.Image;
import com.modiconme.realworld.domain.common.valueobjects.Username;

public record UpdateUserDto(String email, String token, String username, String bio, String image) {

    UpdateUserDto(Email email, String token, Username username, Bio bio, Image image) {
        this(email.getValue(), token, username.getValue(), bio.getValue().orElse(null),
                image.getValue().orElse(null));
    }

}


