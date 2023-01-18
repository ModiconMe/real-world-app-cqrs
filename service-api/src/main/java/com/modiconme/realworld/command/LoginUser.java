package com.modiconme.realworld.command;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.modiconme.realworld.cqrs.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@JsonRootName("user")
public class LoginUser implements Command<LoginUserResult> {

    @Email
    private String email;
    @NotEmpty
    private String password;

}
