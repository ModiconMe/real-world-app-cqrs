package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.profilefollow.FollowProfileRequest;
import com.modiconme.realworld.domain.profilefollow.FollowProfileResult;
import com.modiconme.realworld.domain.profilefollow.FollowProfileService;
import com.modiconme.realworld.domain.profileget.GetProfileRequest;
import com.modiconme.realworld.domain.profileget.GetProfileResult;
import com.modiconme.realworld.domain.profileget.GetProfileService;
import com.modiconme.realworld.domain.profileunfollow.UnfollowProfileRequest;
import com.modiconme.realworld.domain.profileunfollow.UnfollowProfileResult;
import com.modiconme.realworld.domain.profileunfollow.UnfollowProfileService;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/profiles")
public class ProfileController {

    private final GetProfileService getProfileService;
    private final FollowProfileService followProfileService;
    private final UnfollowProfileService unfollowProfileService;

    @PostMapping("/{profileUsername}")
    public ResponseEntity<RestResponse<GetProfileResult>> getProfile(
            @PathVariable String profileUsername, @AuthenticationPrincipal AppUserDetails user
    ) {
        Result<GetProfileResult> result = getProfileService.getProfileByUsername(
                new GetProfileRequest(user.getUserId(), profileUsername)
        );
        return result.toRestResponse();
    }

    @PostMapping("/follow/{profileUsername}")
    public ResponseEntity<RestResponse<FollowProfileResult>> followProfile(
            @PathVariable String profileUsername, @AuthenticationPrincipal AppUserDetails user
    ) {
        Result<FollowProfileResult> result = followProfileService.followProfile(
                new FollowProfileRequest(user.getUserId(), profileUsername)
        );
        return result.toRestResponse();
    }

    @DeleteMapping("/follow/{profileUsername}")
    public ResponseEntity<RestResponse<UnfollowProfileResult>> unfollowProfile(
            @PathVariable String profileUsername, @AuthenticationPrincipal AppUserDetails user
    ) {
        Result<UnfollowProfileResult> result = unfollowProfileService.unfollowProfile(
                new UnfollowProfileRequest(user.getUserId(), profileUsername)
        );
        return result.toRestResponse();
    }

}