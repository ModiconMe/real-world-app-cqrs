package com.modiconme.realworld.domain.profileunfollow;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.FollowRelationId;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import com.modiconme.realworld.domain.common.valueobjects.Username;
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
class UnfollowProfileGateway {

    private final UnfollowProfileRepository unfollowProfileRepository;

    private static Result<FollowedProfile> mapTupleToFollowedProfile(Tuple it) {
        return FollowedProfile.emerge(
                it.get("id", Long.class),
                it.get("username", String.class),
                it.get("bio", String.class),
                it.get("image", String.class),
                it.get("follow_relation_id", Long.class)
        );
    }

    Result<FollowedProfile> findFollowedProfile(Username profileUsername, UserId followedById) {
        return unfollowProfileRepository.findFollowedProfile(followedById.getValue(), profileUsername.getValue())
                .map(UnfollowProfileGateway::mapTupleToFollowedProfile)
                .orElseGet(() -> Result.failure(ApiException.notFound("You are not follow profile %s",
                        profileUsername.getValue())));
    }

    Result<FollowRelationId> unfollowById(FollowRelationId followRelationId) {
        Try<Void> unfollowResult = Try.run(
                () -> unfollowProfileRepository.unfollow(followRelationId.getValue())
        );

        if (unfollowResult.isFailure()) {
            log.error("Deleting follow relation with id {} failed with error: ",
                    followRelationId.getValue(), unfollowResult.getCause());
            return Result.failure(unprocessableEntity("Error occurred while unfollowing profile"));
        }

        return Result.success(followRelationId);
    }
}
