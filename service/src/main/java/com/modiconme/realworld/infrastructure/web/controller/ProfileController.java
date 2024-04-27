package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.followprofile.FollowProfileRequest;
import com.modiconme.realworld.domain.followprofile.FollowProfileResult;
import com.modiconme.realworld.domain.followprofile.FollowProfileService;
import com.modiconme.realworld.domain.followprofile.UnvalidatedFollowProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/profiles")
public class ProfileController {

    private final FollowProfileService followProfileService;

    @PostMapping("/follow")
    public ResponseEntity<RestResponse<FollowProfileResult>> followProfile(
            @RequestBody FollowProfileRequest command
    ) {
        Result<FollowProfileResult> followProfileResult = followProfileService.followProfile(
                new UnvalidatedFollowProfileRequest(command.userUsername(), command.profileUsername()));
        RestResponse<FollowProfileResult> restResponse = RestResponse.of(followProfileResult);
        return followProfileResult.isSuccess()
                ? ResponseEntity.ok(restResponse)
                : ResponseEntity.status(followProfileResult.getStatus()).body(restResponse);
    }

}