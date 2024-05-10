package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.userlogin.LoginUserRequest;
import com.modiconme.realworld.domain.userlogin.LoginUserResponse;
import com.modiconme.realworld.domain.userlogin.LoginUserService;
import com.modiconme.realworld.domain.userregister.RegisterUserRequest;
import com.modiconme.realworld.domain.userregister.RegisterUserResponse;
import com.modiconme.realworld.domain.userregister.RegisterUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final RegisterUserService registerUserService;
    private final LoginUserService loginUserService;

    @PostMapping("/login")
    public ResponseEntity<RestResponse<LoginUserResponse>> login(@RequestBody LoginUserRequest command) {
        Result<LoginUserResponse> loginUserResult = loginUserService.loginUser(command);
        RestResponse<LoginUserResponse> restResponse = RestResponse.of(loginUserResult);
        return loginUserResult.isSuccess()
                ? ResponseEntity.ok(restResponse)
                : ResponseEntity.status(loginUserResult.getStatus()).body(restResponse);
    }

    @PostMapping
    public ResponseEntity<RestResponse<RegisterUserResponse>> register(@RequestBody RegisterUserRequest command) {
        Result<RegisterUserResponse> registerUserResult = registerUserService.registerUser(command);
        RestResponse<RegisterUserResponse> restResponse = RestResponse.of(registerUserResult);
        return registerUserResult.isSuccess()
                ? ResponseEntity.ok(restResponse)
                : ResponseEntity.status(registerUserResult.getStatus()).body(restResponse);
    }

}