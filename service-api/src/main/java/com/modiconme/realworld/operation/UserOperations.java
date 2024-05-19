package com.modiconme.realworld.operation;

import com.modiconme.realworld.query.GetCurrentUserResult;
import org.springframework.web.bind.annotation.GetMapping;

public interface UserOperations {

    @GetMapping("/user")
    GetCurrentUserResult getUser();
}
