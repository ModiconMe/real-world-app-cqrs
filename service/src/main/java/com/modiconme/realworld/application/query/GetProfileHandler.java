package com.modiconme.realworld.application.query;

import com.modiconme.realworld.application.ProfileMapper;
import com.modiconme.realworld.cqrs.QueryHandler;
import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.infrastructure.security.jwt.JwtUtils;
import com.modiconme.realworld.query.GetProfile;
import com.modiconme.realworld.query.GetProfileResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetProfileHandler implements QueryHandler<GetProfileResult, GetProfile> {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public GetProfileResult handle(GetProfile query) {
        String profileUsername = query.getProfileUsername();
        UserEntity profile = userRepository.findByUsername(profileUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "profile with username [%s] is not found", profileUsername));

        // check following
        String userUsername = query.getUserUsername();
        UserEntity user = null;
        if (userUsername != null) {
            user = userRepository.findByUsername(profileUsername)
                    .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with username [%s] is not found", userUsername));
        }

        return new GetProfileResult(ProfileMapper.mapToDto(profile, user));
    }

}
