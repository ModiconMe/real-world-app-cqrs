package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.ProfileMapper;
import com.modiconme.realworld.command.FollowProfile;
import com.modiconme.realworld.command.FollowProfileResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowProfileHandler implements CommandHandler<FollowProfileResult, FollowProfile> {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public FollowProfileResult handle(FollowProfile cmd) {

        UserEntity followee = UserEntity.getExistedUserOrThrow(cmd.getProfileUsername(), userRepository);
        UserEntity follower = UserEntity.getExistedUserOrThrow(cmd.getUserUsername(), userRepository);

        followee = followee.toBuilder().follower(follower.followUser(followee)).build();
        userRepository.save(followee);

        return new FollowProfileResult(ProfileMapper.mapToDto(followee, follower));
    }

}
