package com.modiconme.realworld.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.loginuser.LoginUserRequest;
import com.modiconme.realworld.domain.loginuser.LoginUserResponse;
import com.modiconme.realworld.domain.loginuser.LoginUserService;
import com.modiconme.realworld.domain.loginuser.UnvalidatedLoginUserRequest;
import com.modiconme.realworld.domain.registeruser.RegisterUserRequest;
import com.modiconme.realworld.domain.registeruser.RegisterUserResponse;
import com.modiconme.realworld.domain.registeruser.RegisterUserService;
import com.modiconme.realworld.domain.registeruser.UnvalidatedRegisterUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/")
public class UserController {

    private final RegisterUserService registerUserService;
    private final LoginUserService loginUserService;

    @PostMapping("/user")
    public ResponseEntity<RestResponse<LoginUserResponse>> register(@RequestBody LoginUserRequest command) {
        Result<LoginUserResponse> loginUserResult = loginUserService.loginUser(
                new UnvalidatedLoginUserRequest(command.email(), command.password()));
        RestResponse<LoginUserResponse> restResponse = RestResponse.of(loginUserResult);
        return loginUserResult.isSuccess()
                ? ResponseEntity.ok(restResponse)
                : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(restResponse);
    }

    @PostMapping("/users")
    public ResponseEntity<RestResponse<RegisterUserResponse>> register(@RequestBody RegisterUserRequest command) {
        Result<RegisterUserResponse> registerUserResult = registerUserService.registerUser(
                new UnvalidatedRegisterUserRequest(command.email(), command.username(),
                        command.password()));
        RestResponse<RegisterUserResponse> restResponse = RestResponse.of(registerUserResult);
        return registerUserResult.isSuccess()
                ? ResponseEntity.ok(restResponse)
                : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(restResponse);
    }

}