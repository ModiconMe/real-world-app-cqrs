package com.modiconme.realworld.operation;

import com.modiconme.realworld.command.FollowProfileResult;
import com.modiconme.realworld.command.UnfollowProfileResult;
import com.modiconme.realworld.query.GetProfileResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface ProfileOperations {

    @GetMapping("/profiles/{profile}")
    GetProfileResult getProfile(@PathVariable("profile") String profile);

    @PostMapping("/profiles/{profile}/follow")
    FollowProfileResult followProfile(@PathVariable("profile") String profile);

    @DeleteMapping("/profiles/{profile}/follow")
    UnfollowProfileResult unfollowProfile(@PathVariable("profile") String profile);

}
