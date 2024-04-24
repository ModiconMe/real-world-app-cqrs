package com.modiconme.realworld.domain.registeruser;

import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserEntity;
import com.modiconme.realworld.domain.common.UserRepository;
import com.modiconme.realworld.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Result<RegisterUserResponse> registerUser(UnvalidatedRegisterUserRequest command) {
        return ValidatedRegisterUserRequest.emerge(command, passwordEncoder)
                .flatMap(this::checkEmailExist)
                .map(this::saveUser)
                .map(RegisterUserService::mapToRestResponse);
    }

    private Result<ValidatedRegisterUserRequest> checkEmailExist(ValidatedRegisterUserRequest it) {
        Result<Boolean> existByEmail = userRepository.existByEmail(it.getEmail().getValue());
        return existByEmail.isFailure() ? Result.failure(existByEmail.getError()) : Result.success(it);
    }

    private UserEntity saveUser(ValidatedRegisterUserRequest it) {
        return userRepository.save(UserEntity.register(it)).getData();
    }

    private static RegisterUserResponse mapToRestResponse(UserEntity it) {
        return new RegisterUserResponse(new UserDto(it.getEmail(), "", it.getUsername(), it.getBio(), it.getImage()));
    }
}