package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.followprofile.FollowProfileResult;
import com.modiconme.realworld.domain.followprofile.FollowProfileService;
import com.modiconme.realworld.domain.followprofile.UnvalidatedFollowProfileRequest;
import com.modiconme.realworld.domain.getprofile.GetProfileRequest;
import com.modiconme.realworld.domain.getprofile.GetProfileResult;
import com.modiconme.realworld.domain.getprofile.GetProfileService;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/profiles")
public class ProfileController {

    private final GetProfileService getProfileService;
    private final FollowProfileService followProfileService;

    @PostMapping("/{profileUsername}")
    public ResponseEntity<RestResponse<GetProfileResult>> getProfile(
            @PathVariable String profileUsername, @AuthenticationPrincipal AppUserDetails user
    ) {
        Result<GetProfileResult> getProfileResult = getProfileService.getProfileByUsername(
                new GetProfileRequest(user.getUsername(), profileUsername)
        );
        RestResponse<GetProfileResult> restResponse = RestResponse.of(getProfileResult);
        return getProfileResult.isSuccess()
                ? ResponseEntity.ok(restResponse)
                : ResponseEntity.status(getProfileResult.getStatus()).body(restResponse);
    }

    @PostMapping("/follow/{profileUsername}")
    public ResponseEntity<RestResponse<FollowProfileResult>> followProfile(
            @PathVariable String profileUsername, @AuthenticationPrincipal AppUserDetails user
    ) {
        Result<FollowProfileResult> followProfileResult = followProfileService.followProfile(
                new UnvalidatedFollowProfileRequest(user.getUsername(), profileUsername));
        RestResponse<FollowProfileResult> restResponse = RestResponse.of(followProfileResult);
        return followProfileResult.isSuccess()
                ? ResponseEntity.ok(restResponse)
                : ResponseEntity.status(followProfileResult.getStatus()).body(restResponse);
    }

}