package com.modiconme.realworld.query;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.modiconme.realworld.command.LoginUserResult;
import com.modiconme.realworld.cqrs.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class GetCurrentUser implements Command<LoginUserResult> {

    private String username;

}
