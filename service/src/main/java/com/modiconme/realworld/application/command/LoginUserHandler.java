package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.UserMapper;
import com.modiconme.realworld.command.LoginUser;
import com.modiconme.realworld.command.LoginUserResult;
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
import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.unauthorized;

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
        log.info("Start: login user: [request='{}']", cmd);

        UserDto user = userRepository.findByEmail(cmd.getEmail())
                .filter(u -> passwordEncoder.matches(cmd.getPassword(), u.getPassword()))
                .map(u -> UserMapper.mapToDto(u, jwtUtils.generateAccessToken(AppUserDetails.fromUser(u))))
                .orElseThrow(() -> unauthorized("Wrong credentials"));

        log.info("End: login user [user='{}']", user);
        return new LoginUserResult(user);
    }

}
