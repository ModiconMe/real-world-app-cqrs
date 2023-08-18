package com.modiconme.realworld.command;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.modiconme.realworld.cqrs.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonRootName("user")
public class RegisterUser implements Command<RegisterUserResult> {

    @Email
    @NotEmpty
    @Size(min = 1, max = 128)
    private String email;
    @NotEmpty
    @Size(min = 8, max = 64)
    private String username;
    @NotEmpty
    @Size(min = 8, max = 64)
    private String password;

}
