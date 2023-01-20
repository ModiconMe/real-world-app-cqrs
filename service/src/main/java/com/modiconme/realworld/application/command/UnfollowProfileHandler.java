package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.ProfileMapper;
import com.modiconme.realworld.command.FollowProfile;
import com.modiconme.realworld.command.FollowProfileResult;
import com.modiconme.realworld.command.UnfollowProfile;
import com.modiconme.realworld.command.UnfollowProfileResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.FollowRelationId;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class UnfollowProfileHandler implements CommandHandler<UnfollowProfileResult, UnfollowProfile> {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UnfollowProfileResult handle(UnfollowProfile cmd) {
        String profileUsername = cmd.getProfileUsername();
        UserEntity profile = userRepository.findByUsername(profileUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "profile with username [%s] is not found", profileUsername));

        String userUsername = cmd.getUserUsername();
        UserEntity user = userRepository.findByUsername(profileUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with username [%s] is not found", userUsername));

        // unfollow
        FollowRelationEntity followRelation = FollowRelationEntity.builder()
                .id(new FollowRelationId(user.getId(), profile.getId()))
                .follower(user)
                .followee(profile)
                .build();
        profile.getFollowers().removeIf(follower -> follower.equals(followRelation));
        userRepository.save(profile);
        log.info("user {} unfollow profile {}", user, profile);

        return new UnfollowProfileResult(ProfileMapper.mapToDto(profile, user));
    }

}
