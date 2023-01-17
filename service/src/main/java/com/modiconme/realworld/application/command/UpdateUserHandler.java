package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.UserMapper;
import com.modiconme.realworld.command.RegisterUser;
import com.modiconme.realworld.command.RegisterUserResult;
import com.modiconme.realworld.command.UpdateUser;
import com.modiconme.realworld.command.UpdateUserResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateUserHandler implements CommandHandler<UpdateUserResult, UpdateUser> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UpdateUserResult handle(UpdateUser command) {
        // check username duplicate
        String username = command.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            ApiException e = ApiException.exception(HttpStatus.UNPROCESSABLE_ENTITY, "user with username [%s] is already exists", username);
            log.error(e.getMessage());
            throw e;
        }

        // check email duplicate
        String email = command.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            ApiException e = ApiException.exception(HttpStatus.UNPROCESSABLE_ENTITY, "user with email [%s] is already exists", email);
            log.error(e.getMessage());
            throw e;
        }

        // create user
        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(command.getPassword()))
                .build();
        userRepository.save(user);
        log.info("saved user %s" + user);

        return new UpdateUserResult(UserMapper.mapToDto(user, null));
    }
}
