package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.UserMapper;
import com.modiconme.realworld.command.RegisterUser;
import com.modiconme.realworld.command.RegisterUserResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegisterUserHandler implements CommandHandler<RegisterUserResult, RegisterUser> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public RegisterUserResult handle(RegisterUser cmd) {
        // check username duplicate
        String username = cmd.getUsername();
        if (userRepository.findByUsername(username).isPresent())
            throw exception(HttpStatus.UNPROCESSABLE_ENTITY, "user with username [%s] is already exists", username);

        // check email duplicate
        String email = cmd.getEmail();
        if (userRepository.findByEmail(email).isPresent())
            throw exception(HttpStatus.UNPROCESSABLE_ENTITY, "user with email [%s] is already exists", email);

        // create user
        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(cmd.getPassword()))
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
        userRepository.save(user);
        log.info("saved user {}", user);

        UserDetails userDetails = AppUserDetails.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        return new RegisterUserResult(UserMapper.mapToDto(user, jwtUtils.generateAccessToken(userDetails)));
    }

}
