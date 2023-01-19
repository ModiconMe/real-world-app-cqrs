package com.modiconme.realworld.domain.repository;

import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.UserEntity;

import java.util.List;

public interface FollowRelationRepository {

    List<FollowRelationEntity> findByFollower(UserEntity user);

}
