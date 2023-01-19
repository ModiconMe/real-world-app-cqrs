package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.FollowRelationId;
import com.modiconme.realworld.domain.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DataFollowRelationRepository extends CrudRepository<FollowRelationEntity, FollowRelationId> {

    List<FollowRelationEntity> findByFollower(UserEntity user);

}
