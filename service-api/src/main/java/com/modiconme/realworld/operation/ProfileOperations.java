package com.modiconme.realworld.operation;

import com.modiconme.realworld.command.UnfollowProfileResult;
import com.modiconme.realworld.query.GetProfileResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProfileOperations {

    @GetMapping("/profiles/{profile}")
    GetProfileResult getProfile(@PathVariable("profile") String profile);

    @DeleteMapping("/profiles/{profile}/follow")
    UnfollowProfileResult unfollowProfile(@PathVariable("profile") String profile);

}
