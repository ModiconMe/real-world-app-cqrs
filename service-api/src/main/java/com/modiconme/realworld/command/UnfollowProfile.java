package com.modiconme.realworld.command;

import com.modiconme.realworld.cqrs.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UnfollowProfile implements Command<UnfollowProfileResult> {

    private String userUsername;
    private String profileUsername;

}
