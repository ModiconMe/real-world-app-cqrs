package com.modiconme.realworld.query;

import com.modiconme.realworld.command.LoginUserResult;
import com.modiconme.realworld.cqrs.Command;
import com.modiconme.realworld.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class GetCurrentUserResult implements Command<LoginUserResult> {

    private UserDto user;

}
