package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.UserMapper;
import com.modiconme.realworld.command.RegisterUser;
import com.modiconme.realworld.command.RegisterUserResult;
import com.modiconme.realworld.command.UpdateUser;
import com.modiconme.realworld.command.UpdateUserResult;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateUserHandler implements CommandHandler<UpdateUserResult, UpdateUser> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public UpdateUserResult handle(UpdateUser cmd) {
        // check that current user exist in db
        String currentUsername = cmd.getCurrentUsername();
        UserEntity user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with username [%s] is not found", currentUsername));

        // check username duplicate
        String username = cmd.getUsername();
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent() &&
                !byUsername.get().getId().equals(user.getId()))
            throw exception(HttpStatus.UNPROCESSABLE_ENTITY, "user with username [%s] is already exists", username);

        // check email duplicate
        String email = cmd.getEmail();
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent() &&
                !byEmail.get().getId().equals(user.getId()))
            throw exception(HttpStatus.UNPROCESSABLE_ENTITY, "user with email [%s] is already exists", email);

        // create user
        UserEntity newUser = user.toBuilder()
                .email(cmd.getEmail() != null ? cmd.getEmail() : user.getEmail())
                .username(cmd.getUsername() != null ? cmd.getUsername() : user.getUsername())
                .password(cmd.getPassword() != null ? passwordEncoder.encode(cmd.getPassword()) : user.getPassword())
                .image(cmd.getImage() != null ? cmd.getImage() : user.getImage())
                .bio(cmd.getBio() != null ? cmd.getBio() : user.getBio())
                .updatedAt(ZonedDateTime.now())
                .build();
        userRepository.save(newUser);
        log.info("updated user {}, to new user {}", user, newUser);

        UserDetails userDetails = AppUserDetails.builder()
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .build();

        return new UpdateUserResult(UserMapper.mapToDto(newUser, jwtUtils.generateAccessToken(userDetails)));
    }

}
