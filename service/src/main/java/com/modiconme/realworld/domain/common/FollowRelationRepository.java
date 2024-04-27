package com.modiconme.realworld.domain.common;

public interface FollowRelationRepository {

    Result<FollowRelation> follow(UserEntity follower, UserEntity followee);

}
