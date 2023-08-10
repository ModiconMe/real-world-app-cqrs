package com.modiconme.realworld.application;

import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.ProfileDto;

public class ProfileMapper {

    public static ProfileDto mapToDto(UserEntity profile, UserEntity user) {
        boolean isFollowing = profile.getFollowers().stream()
                .map(FollowRelationEntity::getFollower)
                .anyMatch(follower -> follower.equals(user));

        return ProfileDto.builder()
                .username(profile.getUsername())
                .bio(profile.getBio())
                .image(profile.getImage())
                .following(isFollowing)
                .build();
    }

}
