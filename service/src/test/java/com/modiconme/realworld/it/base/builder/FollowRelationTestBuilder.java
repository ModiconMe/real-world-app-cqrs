package com.modiconme.realworld.it.base.builder;

import com.modiconme.realworld.infrastructure.repository.jpa.entity.FollowRelationEntity;
import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;

public class FollowRelationTestBuilder implements TestBuilder<FollowRelationEntity> {

    private Long id;
    private UserEntity followee;
    private UserEntity follower;

    public static FollowRelationTestBuilder aFollowRelation() {
        return new FollowRelationTestBuilder();
    }

    @Override
    public FollowRelationEntity build() {
        return new FollowRelationEntity(id, followee, follower);
    }

    public Long getId() {
        return id;
    }

    public FollowRelationTestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UserEntity getFollowee() {
        return followee;
    }

    public FollowRelationTestBuilder followee(UserEntity followee) {
        this.followee = followee;
        return this;
    }

    public UserEntity getFollower() {
        return follower;
    }

    public FollowRelationTestBuilder follower(UserEntity follower) {
        this.follower = follower;
        return this;
    }
}
