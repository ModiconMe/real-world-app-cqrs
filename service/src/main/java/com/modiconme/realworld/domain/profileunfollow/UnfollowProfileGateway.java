package com.modiconme.realworld.domain.profileunfollow;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.FollowRelationId;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    // TODO replace with native cause select before delete
    Result<FollowRelationId> unfollowById(FollowRelationId followRelationId) {
        try {
            unfollowProfileRepository.deleteById(followRelationId.getValue());
            return Result.success(followRelationId);
        } catch (Exception e) {
            return Result.failure(e);
        }
    }
}
