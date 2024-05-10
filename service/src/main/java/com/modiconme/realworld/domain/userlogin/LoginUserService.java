package com.modiconme.realworld.domain.userlogin;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserService {

    private final LoginUserGateway loginUserGateway;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Result<LoginUserResponse> loginUser(LoginUserRequest request) {
        return ValidatedLoginUserRequest.emerge(request)
                .flatMap(this::findUserByEmailAndMatchPassword)
                .map(this::mapToLoginUserResult);
    }

    private Result<AuthorizedUser> findUserByEmailAndMatchPassword(ValidatedLoginUserRequest req) {
        return loginUserGateway.findByEmail(req.getEmail().getValue())
                .flatMap(it -> AuthorizedUser.emerge(it, req.getPassword(), passwordEncoder));
    }

    private LoginUserResponse mapToLoginUserResult(AuthorizedUser it) {
        var appUserDetails = new AppUserDetails(
                it.getExistedByEmailUser().getUserId().getValue(),
                it.getExistedByEmailUser().getEmail().getValue(),
                it.getExistedByEmailUser().getUsername().getValue(),
                it.getExistedByEmailUser().getEncodedPassword().getValue());
        return new LoginUserResponse(
                new LoginUserDto(
                        it.getExistedByEmailUser().getEmail(),
                        tokenService.getAccessToken(appUserDetails),
                        it.getExistedByEmailUser().getUsername(),
                        it.getExistedByEmailUser().getBio(),
                        it.getExistedByEmailUser().getImage()
                )
        );
    }
}
