package com.modiconme.realworld.domain.followprofile;

import com.modiconme.realworld.domain.common.FollowRelationRepository;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserRepository;
import com.modiconme.realworld.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowProfileService {

    private final UserRepository userRepository;
    private final FollowRelationRepository followRelationRepository;

    public Result<FollowProfileResult> followProfile(UnvalidatedFollowProfileRequest request) {
        return ValidatedFollowProfileRequest.emerge(request)
                .flatMap(this::findFollowerAndFolloweeProfiles)
                .flatMap(this::createFollowRelation);
    }

    private Result<FollowerAndFolloweeProfiles> findFollowerAndFolloweeProfiles(ValidatedFollowProfileRequest validatedFollowProfileRequest) {
        return FollowerAndFolloweeProfiles.emerge(
                userRepository.findByUsername(validatedFollowProfileRequest.getProfileUsername().getValue()),
                userRepository.findByUsername(validatedFollowProfileRequest.getCurrentUsername().getValue())
        );
    }

    private Result<FollowProfileResult> createFollowRelation(FollowerAndFolloweeProfiles followerAndFolloweeProfiles) {
        return followRelationRepository.follow(
                        followerAndFolloweeProfiles.getFollower(),
                        followerAndFolloweeProfiles.getFollowee())
                .map(followRelation -> new FollowProfileResult(
                                new ProfileDto(
                                        followRelation.getFollowee().getUsername(),
                                        followRelation.getFollowee().getBio(),
                                        followRelation.getFollowee().getImage(), true)
                        )
                );

    }
}
