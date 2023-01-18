package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.ProfileMapper;
import com.modiconme.realworld.application.UserMapper;
import com.modiconme.realworld.command.FollowProfile;
import com.modiconme.realworld.command.FollowProfileResult;
import com.modiconme.realworld.command.LoginUser;
import com.modiconme.realworld.command.LoginUserResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.FollowRelationId;
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

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowProfileHandler implements CommandHandler<FollowProfileResult, FollowProfile> {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public FollowProfileResult handle(FollowProfile cmd) {
        String profileUsername = cmd.getProfileUsername();
        UserEntity profile = userRepository.findByUsername(profileUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "profile with username [%s] is not found", profileUsername));

        String userUsername = cmd.getUserUsername();
        UserEntity user = userRepository.findByUsername(profileUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with username [%s] is not found", userUsername));

        // follow
        profile = profile.toBuilder()
                .follower(FollowRelationEntity.builder()
                        .id(new FollowRelationId(user.getId(), profile.getId()))
                        .follower(user)
                        .followee(profile)
                        .build())
                .build();
        userRepository.save(profile);

        return new FollowProfileResult(ProfileMapper.mapToDto(profile, user));
    }

}
