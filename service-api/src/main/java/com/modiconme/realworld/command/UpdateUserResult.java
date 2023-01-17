package com.modiconme.realworld.command;

import com.modiconme.realworld.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserResult {

    private UserDto user;

}
