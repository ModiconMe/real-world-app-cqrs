package com.modiconme.realworld.domain.profilefollow;

import com.modiconme.realworld.domain.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowProfileService {

    private final FollowProfileGateway followProfileGateway;

    public Result<FollowProfileResult> followProfile(FollowProfileRequest request) {
        return ValidatedFollowProfileRequest.emerge(request)
                .flatMap(this::findProfileToFollow)
                .flatMap(this::createFollowRelation);
    }

    private Result<UnfollowedProfile> findProfileToFollow(
            ValidatedFollowProfileRequest validatedFollowProfileRequest
    ) {
        return followProfileGateway.findUnfollowedProfile(
                validatedFollowProfileRequest.getProfileUsername(),
                validatedFollowProfileRequest.getCurrentUserId()
        );
    }

    private Result<FollowProfileResult> createFollowRelation(UnfollowedProfile unfollowedProfile) {
        return followProfileGateway.follow(
                        unfollowedProfile.getFollowerId(),
                        unfollowedProfile.getFolloweeId()
                )
                .map(followRelation -> new FollowProfileResult(
                        new FollowedProfileDto(
                                unfollowedProfile.getUsername(),
                                unfollowedProfile.getBio(),
                                unfollowedProfile.getImage()
                        )
                        )
                );

    }
}
