package com.modiconme.realworld.application.query;

import com.modiconme.realworld.application.UserMapper;
import com.modiconme.realworld.cqrs.QueryHandler;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.JwtUtils;
import com.modiconme.realworld.query.GetCurrentUser;
import com.modiconme.realworld.query.GetCurrentUserResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetCurrentUserHandler implements QueryHandler<GetCurrentUserResult, GetCurrentUser> {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional(readOnly = true)
    public GetCurrentUserResult handle(GetCurrentUser query) {
        UserEntity user = UserEntity.getExistedUserOrThrow(query.getCurrentUsername(), userRepository);
        return new GetCurrentUserResult(UserMapper.mapToDto(
                user, jwtUtils.generateAccessToken(AppUserDetails.fromUser(user))));
    }

}
