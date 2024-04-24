package com.modiconme.realworld.operation;

import com.modiconme.realworld.command.UpdateUser;
import com.modiconme.realworld.command.UpdateUserResult;
import com.modiconme.realworld.query.GetCurrentUserResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserOperations {

    @GetMapping("/user")
    GetCurrentUserResult getUser();

    @PutMapping("/user")
    UpdateUserResult updateUser(@RequestBody @Valid UpdateUser command);
}
