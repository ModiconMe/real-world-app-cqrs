package com.modiconme.realworld.domain.profilefollow;

import com.modiconme.realworld.domain.common.valueobjects.Bio;
import com.modiconme.realworld.domain.common.valueobjects.Image;
import com.modiconme.realworld.domain.common.valueobjects.Username;

public record FollowedProfileDto(String username, String bio, String image, Boolean following) {

    public FollowedProfileDto(Username username, Bio bio, Image image) {
        this(username.getValue(), bio.getValue().orElse(null), image.getValue().orElse(null), true);
    }
}


