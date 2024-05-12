package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.userlogin.LoginUserRequest;
import com.modiconme.realworld.domain.userlogin.LoginUserResponse;
import com.modiconme.realworld.domain.userlogin.LoginUserService;
import com.modiconme.realworld.domain.userregister.RegisterUserRequest;
import com.modiconme.realworld.domain.userregister.RegisterUserResponse;
import com.modiconme.realworld.domain.userregister.RegisterUserService;
import com.modiconme.realworld.domain.userupdate.UpdateUserRequest;
import com.modiconme.realworld.domain.userupdate.UpdateUserResponse;
import com.modiconme.realworld.domain.userupdate.UpdateUserService;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class UserController {

    private final RegisterUserService registerUserService;
    private final LoginUserService loginUserService;
    private final UpdateUserService updateUserService;

    @PostMapping("/users/login")
    public ResponseEntity<RestResponse<LoginUserResponse>> login(
            @RequestBody LoginUserRequest command
    ) {
        return loginUserService.loginUser(command).toRestResponse();
    }

    @PostMapping("/users")
    public ResponseEntity<RestResponse<RegisterUserResponse>> register(
            @RequestBody RegisterUserRequest command
    ) {
        return registerUserService.registerUser(command).toRestResponse();
    }

    @PutMapping("/user")
    public ResponseEntity<RestResponse<UpdateUserResponse>> update(
            @RequestBody UpdateUserRequest command, @AuthenticationPrincipal AppUserDetails user
    ) {
        return updateUserService.updateUser(user.getUserId(), command).toRestResponse();
    }

}