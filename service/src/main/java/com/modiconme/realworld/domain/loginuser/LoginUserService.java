package com.modiconme.realworld.domain.loginuser;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserRepository;
import com.modiconme.realworld.dto.UserDto;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Result<LoginUserResponse> loginUser(UnvalidatedLoginUserRequest request) {
        return ValidatedLoginUserRequest.emerge(request)
                .flatMap(this::validateCredentials)
                .map(this::mapToLoginUserResult);
    }

    private Result<LoginUser> validateCredentials(ValidatedLoginUserRequest req) {
        return userRepository.findByEmail(req.getEmail().getValue())
                .flatMap(el -> LoginUser.emerge(el, req.getPassword(), passwordEncoder));
    }

    private LoginUserResponse mapToLoginUserResult(LoginUser it) {
        return new LoginUserResponse(
                new UserDto(
                        it.getUserEntity().getEmail(),
                        tokenService.getAccessToken(AppUserDetails.fromUser(it.getUserEntity())),
                        it.getUserEntity().getUsername(),
                        it.getUserEntity().getBio(),
                        it.getUserEntity().getImage()
                )
        );
    }
}
