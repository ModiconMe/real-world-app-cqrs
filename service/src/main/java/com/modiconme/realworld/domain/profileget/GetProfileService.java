package com.modiconme.realworld.domain.profileget;

import com.modiconme.realworld.domain.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetProfileService {

    private final GetProfileGateway profileGateway;

    @Transactional(readOnly = true)
    public Result<GetProfileResult> getProfileByUsername(GetProfileRequest request) {
        return ValidatedGetProfileRequest.emerge(request)
                .flatMap(this::getProfileDtoResult)
                .map(GetProfileResult::new);
    }

    private Result<ProfileDto> getProfileDtoResult(ValidatedGetProfileRequest it) {
        return profileGateway.findProfile(it.getProfileUsername(), it.getCurrentUserId());
    }
}
