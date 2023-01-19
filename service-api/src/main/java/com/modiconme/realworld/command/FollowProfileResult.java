package com.modiconme.realworld.command;

import com.modiconme.realworld.dto.ProfileDto;
import com.modiconme.realworld.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FollowProfileResult {

    private ProfileDto profile;

}
