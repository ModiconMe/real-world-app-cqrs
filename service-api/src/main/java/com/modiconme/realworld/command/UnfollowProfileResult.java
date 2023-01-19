package com.modiconme.realworld.command;

import com.modiconme.realworld.dto.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UnfollowProfileResult {

    private ProfileDto profile;

}
