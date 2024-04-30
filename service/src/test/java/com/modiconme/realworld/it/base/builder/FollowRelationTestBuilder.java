package com.modiconme.realworld.it.base.builder;

import com.modiconme.realworld.domain.common.FollowRelation;
import com.modiconme.realworld.domain.common.UserEntity;

public class FollowRelationTestBuilder implements TestBuilder<FollowRelation> {

    private Long id;
    private UserEntity followee;
    private UserEntity follower;

    public static FollowRelationTestBuilder aFollowRelation() {
        return new FollowRelationTestBuilder();
    }

    @Override
    public FollowRelation build() {
        return new FollowRelation(id, followee, follower);
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
