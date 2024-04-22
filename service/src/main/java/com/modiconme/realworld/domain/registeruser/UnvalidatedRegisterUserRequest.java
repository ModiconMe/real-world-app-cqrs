package com.modiconme.realworld.domain.registeruser;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.modiconme.realworld.cqrs.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class UnvalidatedRegisterUserRequest {
    private final String email;
    private final String username;
    private final String password;
}
