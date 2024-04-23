package com.modiconme.realworld.domain.registeruser;

import com.modiconme.realworld.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record RegisterUserResponse(UserDto user) {
}
