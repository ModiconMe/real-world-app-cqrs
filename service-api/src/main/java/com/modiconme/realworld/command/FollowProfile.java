package com.modiconme.realworld.command;

import com.modiconme.realworld.cqrs.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FollowProfile implements Command<FollowProfileResult> {

    private String userUsername;
    private String profileUsername;

}
