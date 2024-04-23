package com.modiconme.realworld.domain.loginuser;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.registeruser.LoginUser;
import com.modiconme.realworld.domain.registeruser.UserRepository;
import com.modiconme.realworld.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserService {

    private final UserRepository userRepository;

    public Result<LoginUserResponse> loginUser(UnvalidatedLoginUserRequest request) {
        return ValidatedLoginUserRequest.emerge(request)
                .flatMap(this::validateCredentials)
                .map(LoginUserService::mapToLoginUserResult);
    }

    private Result<LoginUser> validateCredentials(ValidatedLoginUserRequest req) {
        return userRepository.findByEmail(req.getEmail().getValue())
                .flatMap(el -> LoginUser.emerge(el, req.getPassword()));
    }

    private static LoginUserResponse mapToLoginUserResult(LoginUser el) {
        return new LoginUserResponse(new UserDto(el.getUserEntity().getEmail(), "",
                el.getUserEntity().getUsername(), el.getUserEntity().getBio(), el.getUserEntity().getImage()));
    }
}
