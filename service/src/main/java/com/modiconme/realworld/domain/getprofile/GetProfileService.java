package com.modiconme.realworld.domain.getprofile;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserRepository;
import com.modiconme.realworld.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProfileService {

    private final UserRepository userRepository;

    public Result<GetProfileResult> getProfileByUsername(GetProfileRequest request) {
        return ValidatedGetProfileRequest.emerge(request)
                .flatMap(this::getProfileDtoResult)
                .map(GetProfileResult::new);
    }

    private Result<ProfileDto> getProfileDtoResult(ValidatedGetProfileRequest it) {
        return userRepository.findProfile(
                it.getProfileUsername().getValue(),
                it.getCurrentUserUsername().getValue()
        );
    }
}
