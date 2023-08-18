package com.modiconme.realworld.command;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.modiconme.realworld.cqrs.Command;
import jakarta.validation.constraints.Email;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonRootName("user")
public class UpdateUser implements Command<UpdateUserResult> {

    @With
    private long userId;

    @Email
    private String email;
    private String username;
    private String password;
    private String bio;
    private String image;

}
