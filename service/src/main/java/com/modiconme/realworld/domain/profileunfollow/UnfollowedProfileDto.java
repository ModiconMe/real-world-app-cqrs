package com.modiconme.realworld.domain.profileunfollow;

import com.modiconme.realworld.domain.common.valueobjects.Bio;
import com.modiconme.realworld.domain.common.valueobjects.Image;
import com.modiconme.realworld.domain.common.valueobjects.Username;

public record UnfollowedProfileDto(String username, String bio, String image, Boolean following) {

    public UnfollowedProfileDto(Username username, Bio bio, Image image) {
        this(username.getValue(), bio.getValue().orElse(null), image.getValue().orElse(null), false);
    }
}


