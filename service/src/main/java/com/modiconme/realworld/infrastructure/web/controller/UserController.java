package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.application.service.AuthenticationContextHolderService;
import com.modiconme.realworld.command.*;
import com.modiconme.realworld.cqrs.Bus;
import com.modiconme.realworld.operation.UserOperations;
import com.modiconme.realworld.query.GetCurrentUser;
import com.modiconme.realworld.query.GetCurrentUserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/")
public class UserController implements UserOperations {

    private final AuthenticationContextHolderService authService;
    private final Bus bus;

    @Override
    public RegisterUserResult register(RegisterUser command) {
        return bus.executeCommand(command);
    }

    @Override
    public LoginUserResult login(LoginUser command) {
        return bus.executeCommand(command);
    }

    @Override
    public GetCurrentUserResult getUser() {
        return bus.executeQuery(new GetCurrentUser(authService.getCurrentUsername()));
    }

    @Override
    public UpdateUserResult updateUser(UpdateUser command) {
        return bus.executeCommand(command.withCurrentUsername(authService.getCurrentUsername()));
    }

}