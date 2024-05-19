package com.modiconme.realworld.domain.profilefollow;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.*;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
class FollowProfileGateway {

    private final FollowProfileRepository repository;

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
        return repository.findUnfollowedProfile(followedBy.getValue(), profileUsername.getValue())
                .map(it -> mapTupleToUnfollowedProfile(it, followedBy))
                .orElseGet(() -> Result.failure(ApiException.notFound("You are already follow profile %s",
                        profileUsername.getValue())));
    }

    Result<FollowRelationId> follow(FollowerId followerId, FolloweeId followeeId) {
        return Result.runCatching(
                        () -> repository.upsert(followerId.getValue(), followeeId.getValue()),
                        t -> log.error("Error occurred while save following relation: ", t)
                )
                .onFailure(t -> log.error("Following profile {} by user {} failed with error: ",
                        followeeId.getValue(), followerId.getValue(), t))
                .flatMap(FollowRelationId::emerge);
    }
}
