package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.UserMapper;
import com.modiconme.realworld.command.LoginUser;
import com.modiconme.realworld.command.LoginUserResult;
import com.modiconme.realworld.command.RegisterUser;
import com.modiconme.realworld.command.RegisterUserResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.JwtUtils;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginUserHandler implements CommandHandler<LoginUserResult, LoginUser> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public LoginUserResult handle(LoginUser command) {
        // check email exists
        String email = command.getEmail();
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            ApiException e = ApiException.exception(HttpStatus.NOT_FOUND, "user with email [%s] is not found", email);
            log.error(e.getMessage());
            throw e;
        }

        // check password
        UserEntity user = optionalUser.get();
        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            ApiException e = ApiException.exception(HttpStatus.NOT_FOUND, "user with that combination of email and password is not found", email);
            log.error(e.getMessage());
            throw e;
        }

        // to generate jwt token
        UserDetails userDetails = AppUserDetails.builder()
                .id(user.getId())
                .email(email)
                .password(user.getPassword())
                .build();

        return new LoginUserResult(UserMapper.mapToDto(user, jwtUtils.generateAccessToken(userDetails)));
    }
}
