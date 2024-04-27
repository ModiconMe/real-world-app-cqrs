package com.modiconme.realworld.infrastructure.repository.jpa;

import com.modiconme.realworld.domain.common.FollowRelation;
import com.modiconme.realworld.domain.common.FollowRelationRepository;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaFollowRelationRelationRepositoryAdapter implements FollowRelationRepository {

    private final DataFollowRelationRepository dataFollowRelationRepository;

    @Override
    public Result<FollowRelation> follow(UserEntity follower, UserEntity followee) {
        int id = dataFollowRelationRepository.upsert(follower.getId(), followee.getId());
        return id != 0
                ? Result.success(new FollowRelation(null, followee, follower))
                : Result.failure(ApiException.unprocessableEntity("User already following this profile"));
    }
}
