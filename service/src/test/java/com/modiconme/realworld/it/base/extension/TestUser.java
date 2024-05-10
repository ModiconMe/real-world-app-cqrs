package com.modiconme.realworld.it.base.extension;

import com.modiconme.realworld.domain.common.valueobjects.Bio;
import com.modiconme.realworld.domain.common.valueobjects.Email;
import com.modiconme.realworld.domain.common.valueobjects.Image;
import com.modiconme.realworld.domain.common.valueobjects.Username;

public record TestUser(String email, String token, String username, String bio, String image) {

    TestUser(Email email, String token, Username username, Bio bio, Image image) {
        this(email.getValue(), token, username.getValue(), bio.getValue().orElse(null), image.getValue().orElse(null));
    }
}


