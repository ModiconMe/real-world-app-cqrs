package com.modiconme.realworld.query;

import com.modiconme.realworld.dto.ProfileDto;
import com.modiconme.realworld.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetProfileResult {

    private ProfileDto profile;

}
