package com.modiconme.realworld.domain.profileget;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.notFound;

@RequiredArgsConstructor
@Repository
class GetProfileGateway {

    private final GetProfileRepository repository;

    private static ProfileDto mapTupleToProfileDto(Tuple it) {
        return new ProfileDto(
                it.get("username", String.class),
                it.get("bio", String.class),
                it.get("image", String.class),
                it.get("following", Boolean.class)
        );
    }

    Result<ProfileDto> findProfile(Username profileUsername, UserId currentUserId) {
        return repository.findProfile(currentUserId.getValue(), profileUsername.getValue())
                .map(GetProfileGateway::mapTupleToProfileDto)
                .map(Result::success)
                .orElseGet(() -> Result.failure(notFound("Profile with username %s not exists",
                        profileUsername.getValue())));
    }
}
