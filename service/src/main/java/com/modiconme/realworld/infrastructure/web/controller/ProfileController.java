package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.application.service.AuthenticationContextHolderService;
import com.modiconme.realworld.command.FollowProfile;
import com.modiconme.realworld.command.FollowProfileResult;
import com.modiconme.realworld.command.UnfollowProfile;
import com.modiconme.realworld.command.UnfollowProfileResult;
import com.modiconme.realworld.cqrs.Bus;
import com.modiconme.realworld.operation.ProfileOperations;
import com.modiconme.realworld.query.GetProfile;
import com.modiconme.realworld.query.GetProfileResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/")
public class ProfileController implements ProfileOperations {

    private final Bus bus;
    private final AuthenticationContextHolderService authService;

    @Override
    public GetProfileResult getProfile(String profile) {
        return bus.executeQuery(new GetProfile(authService.getCurrentUsername(), profile));
    }

    @Override
    public FollowProfileResult followProfile(String profile) {
        return bus.executeCommand(new FollowProfile(authService.getCurrentUsername(), profile));
    }

    @Override
    public UnfollowProfileResult unfollowProfile(String profile) {
        return bus.executeCommand(new UnfollowProfile(authService.getCurrentUsername(), profile));
    }

}
