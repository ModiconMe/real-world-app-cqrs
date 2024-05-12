package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService {

    private final UpdateUserGateway updateUserGateway;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public Result<UpdateUserResponse> updateUser(long userId, UpdateUserRequest request) {
        return ValidatedUpdateUserRequest.emerge(userId, request)
                .flatMap(this::getPreparedForUpdateUser)
                .flatMap(updateUserGateway::checkEmailAndUsernameUnique)
                .flatMap(updateUserGateway::updateUser)
                .map(this::mapToUpdateUserResult);
    }

    private Result<PreparedForUpdateUser> getPreparedForUpdateUser(ValidatedUpdateUserRequest request) {
        return updateUserGateway.findUserToUpdate(request.getUserId())
                .map(it -> PreparedForUpdateUser.of(request, it, passwordEncoder));
    }

    private UpdateUserResponse mapToUpdateUserResult(PreparedForUpdateUser it) {
        var appUserDetails = new AppUserDetails(
                it.getUserId().getValue(),
                it.getEmail().getValue(),
                it.getUsername().getValue(),
                it.getPassword().getValue());

        return new UpdateUserResponse(
                new UpdateUserDto(
                        it.getEmail(),
                        tokenService.getAccessToken(appUserDetails),
                        it.getUsername(),
                        it.getBio(),
                        it.getImage()
                )
        );
    }
}
