package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.UserMapper;
import com.modiconme.realworld.command.LoginUser;
import com.modiconme.realworld.command.LoginUserResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginUserHandler implements CommandHandler<LoginUserResult, LoginUser> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional(readOnly = true)
    public LoginUserResult handle(LoginUser cmd) {
        // check email exists
        String email = cmd.getEmail();
        UserEntity user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with email [%s] is not found", email));

        // check password
        if (!passwordEncoder.matches(cmd.getPassword(), user.getPassword()))
            throw exception(HttpStatus.UNAUTHORIZED, "user with that combination of email and password is not found", email);

        // to generate jwt token
        log.info("login user {}", user);

        return new LoginUserResult(UserMapper.mapToDto(user, jwtUtils.generateAccessToken(AppUserDetails.fromUser(user))));
    }

}
