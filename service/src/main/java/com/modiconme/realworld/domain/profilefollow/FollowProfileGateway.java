package com.modiconme.realworld.domain.profilefollow;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.*;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class FollowProfileGateway {

    private final FollowProfileRepository followProfileRepository;

    private static Result<UnfollowedProfile> mapTupleToUnfollowedProfile(Tuple it, UserId userId) {
        return UnfollowedProfile.emerge(
                it.get("id", Long.class),
                userId,
                it.get("username", String.class),
                it.get("bio", String.class),
                it.get("image", String.class)
        );
    }

    Result<UnfollowedProfile> findUnfollowedProfile(Username profileUsername, UserId followedBy) {
        return followProfileRepository.findUnfollowedProfile(followedBy.getValue(), profileUsername.getValue())
                .map(it -> mapTupleToUnfollowedProfile(it, followedBy))
                .orElseGet(() -> Result.failure(ApiException.notFound("You are already follow profile %s",
                        profileUsername.getValue())));
    }

    Result<FollowRelationId> follow(FollowerId followerId, FolloweeId followeeId) {
        long id = followProfileRepository.upsert(followerId.getValue(), followeeId.getValue());
        return id != 0
                ? FollowRelationId.emerge(id)
                : Result.failure(ApiException.unprocessableEntity("User already following this profile"));
    }
}
