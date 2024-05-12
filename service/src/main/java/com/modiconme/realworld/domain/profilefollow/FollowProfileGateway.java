package com.modiconme.realworld.domain.profilefollow;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.*;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import io.vavr.control.Try;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.unprocessableEntity;

@Slf4j
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
        Try<Long> followResult = Try.of(
                () -> followProfileRepository.upsert(followerId.getValue(), followeeId.getValue())
        );

        if (followResult.isFailure()) {
            log.error("Following profile {} by user {} failed with error: ",
                    followeeId.getValue(), followerId.getValue(), followResult.getCause());
            return Result.failure(unprocessableEntity("Error occurred while following profile"));
        }

        return followResult.get() != 0
                ? FollowRelationId.emerge(followResult.get())
                : Result.failure(ApiException.unprocessableEntity("Oops! Profile was not followed"));
    }
}
