package com.modiconme.realworld.application.query;

import com.modiconme.realworld.application.ProfileMapper;
import com.modiconme.realworld.cqrs.QueryHandler;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.query.GetProfile;
import com.modiconme.realworld.query.GetProfileResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetProfileHandler implements QueryHandler<GetProfileResult, GetProfile> {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public GetProfileResult handle(GetProfile query) {
        UserEntity profile = UserEntity.getExistedUserOrThrow(query.getProfileUsername(), userRepository);
        UserEntity currentUser = UserEntity.getExistedUserOrThrow(query.getCurrentUsername(), userRepository);
        return new GetProfileResult(ProfileMapper.mapToDto(profile, currentUser));
    }

}
