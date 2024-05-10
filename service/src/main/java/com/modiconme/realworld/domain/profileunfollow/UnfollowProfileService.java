package com.modiconme.realworld.domain.profileunfollow;

import com.modiconme.realworld.domain.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UnfollowProfileService {

    private final UnfollowProfileGateway unfollowProfileGateway;

    private static UnfollowProfileResult mapToResult(FollowedProfile followedProfile) {
        return new UnfollowProfileResult(
                new UnfollowedProfileDto(
                        followedProfile.getUsername(),
                        followedProfile.getBio(),
                        followedProfile.getImage()
                )
        );
    }

    public Result<UnfollowProfileResult> unfollowProfile(UnfollowProfileRequest request) {
        return ValidatedUnfollowProfileRequest.emerge(request)
                .flatMap(this::findFollowedProfile)
                .flatMap(this::deleteFollowRelation);
    }

    private Result<FollowedProfile> findFollowedProfile(ValidatedUnfollowProfileRequest it) {
        return unfollowProfileGateway.findFollowedProfile(it.getProfileUsername(), it.getUserId());
    }

    private Result<UnfollowProfileResult> deleteFollowRelation(FollowedProfile followedProfile) {
        return unfollowProfileGateway.unfollowById(followedProfile.getFollowRelationId())
                .map(profile -> mapToResult(followedProfile));

    }
}
