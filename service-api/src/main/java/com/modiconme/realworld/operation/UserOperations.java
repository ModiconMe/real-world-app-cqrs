package com.modiconme.realworld.operation;

import com.modiconme.realworld.command.*;
import com.modiconme.realworld.query.GetCurrentUser;
import com.modiconme.realworld.query.GetCurrentUserResult;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserOperations {
    @PostMapping("/users")
    RegisterUserResult register(@RequestBody @Valid RegisterUser command);

    @PostMapping("/users/login")
    LoginUserResult login(@RequestBody @Valid LoginUser command);

    @GetMapping("/user")
    GetCurrentUserResult getUser();

    @PutMapping("/user")
    UpdateUserResult updateUser(@RequestBody @Valid UpdateUser command);
}
