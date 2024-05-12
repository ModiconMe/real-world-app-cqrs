package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.valueobjects.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class PreparedForUpdateUser {
    private final UserId userId;
    private final Email email;
    private final Username username;
    private final EncodedPassword password;
    private final Image image;
    private final Bio bio;

    static PreparedForUpdateUser of(
            ValidatedUpdateUserRequest request, ExistedByIdUser user, PasswordEncoder passwordEncoder
    ) {
        Email email = request.getEmail().mapToEmail(user.getEmail());
        Username username = request.getUsername().mapToUsername(user.getUsername());
        EncodedPassword password = request.getPassword().mapToEncodedPassword(
                passwordEncoder, user.getEncodedPassword()
        );

        return new PreparedForUpdateUser(
                request.getUserId(),
                email,
                username,
                password,
                request.getImage(),
                request.getBio()
        );
    }
}
