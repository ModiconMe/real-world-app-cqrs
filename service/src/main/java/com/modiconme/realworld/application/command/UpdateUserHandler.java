package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.UserMapper;
import com.modiconme.realworld.command.UpdateUser;
import com.modiconme.realworld.command.UpdateUserResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.dto.UserDto;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.notFound;
import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.unprocessableEntity;

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
        log.info("Start: update user: [request='{}']", cmd);

        if (userRepository.findByEmailOrUsername(cmd.getEmail(), cmd.getUsername()).stream()
                .anyMatch(u -> u.getId() != cmd.getUserId())) {
            throw unprocessableEntity("User is already exists");
        }

        UserDto user = userRepository.findById(cmd.getUserId())
                .map(u -> u.updateUser(cmd, passwordEncoder))
                .map(u -> UserMapper.mapToDto(u, jwtUtils.generateAccessToken(AppUserDetails.fromUser(u))))
                .orElseThrow(() -> notFound("User not found"));

        log.info("End: update user: [user='{}']", user);
        return new UpdateUserResult(user);
    }

}
