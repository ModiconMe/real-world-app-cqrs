package com.modiconme.realworld.domain.userregister;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final RegisterUserGateway registerUserGateway;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Result<RegisterUserResponse> registerUser(RegisterUserRequest command) {
        return ValidatedRegisterUserRequest.emerge(command, passwordEncoder)
                .flatMap(this::checkEmailExist)
                .map(this::saveUser)
                .map(this::mapToRestResponse);
    }

    private Result<ValidatedRegisterUserRequest> checkEmailExist(ValidatedRegisterUserRequest it) {
        Result<Boolean> existByEmail = registerUserGateway.existByEmail(it.getEmail().getValue());
        return existByEmail.isFailure() ? Result.failure(existByEmail.getError()) : Result.success(it);
    }

    private RegisteredUser saveUser(ValidatedRegisterUserRequest it) {
        return registerUserGateway.save(it).getData();
    }

    private RegisterUserResponse mapToRestResponse(RegisteredUser it) {
        var appUserDetails = new AppUserDetails(
                it.getUserId().getValue(),
                it.getEmail().getValue(),
                it.getUsername().getValue(),
                it.getEncodedPassword().getValue());
        return new RegisterUserResponse(
                new RegisteredUserDto(
                        it.getEmail(),
                        tokenService.getAccessToken(appUserDetails),
                        it.getUsername()
                )
        );
    }
}
