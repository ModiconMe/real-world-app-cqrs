package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.ProfileMapper;
import com.modiconme.realworld.command.UnfollowProfile;
import com.modiconme.realworld.command.UnfollowProfileResult;
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
public class UnfollowProfileHandler implements CommandHandler<UnfollowProfileResult, UnfollowProfile> {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UnfollowProfileResult handle(UnfollowProfile cmd) {


        UserEntity followee = UserEntity.getExistedUserOrThrow(cmd.getProfileUsername(), userRepository);
        UserEntity follower = UserEntity.getExistedUserOrThrow(cmd.getUserUsername(), userRepository);

        followee.getFollowers().removeIf(f -> f.getId().getIdFollower() == follower.getId());
        userRepository.save(followee);

        return new UnfollowProfileResult(ProfileMapper.mapToDto(followee, follower));
    }

}
