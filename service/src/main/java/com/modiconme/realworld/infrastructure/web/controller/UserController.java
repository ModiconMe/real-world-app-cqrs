package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.registeruser.RegisterUserRequest;
import com.modiconme.realworld.domain.registeruser.RegisterUserResponse;
import com.modiconme.realworld.domain.registeruser.RegisterUserService;
import com.modiconme.realworld.domain.registeruser.UnvalidatedRegisterUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/")
public class UserController {

    private final RegisterUserService registerUserService;

    @PostMapping("/users")
    public Result<RegisterUserResponse> register(RegisterUserRequest command) {
        return registerUserService.registerUser(new UnvalidatedRegisterUserRequest(command.getEmail(),
                command.getUsername(), command.getPassword()));
    }

}