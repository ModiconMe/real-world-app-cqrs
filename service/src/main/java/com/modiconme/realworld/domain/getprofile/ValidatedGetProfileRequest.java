package com.modiconme.realworld.domain.getprofile;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class ValidatedGetProfileRequest {

    private final Username currentUserUsername;
    private final Username profileUsername;

    public static Result<ValidatedGetProfileRequest> emerge(GetProfileRequest request) {
        return wrapPrimitivesToValueObject(request.currentUsername(), request.profileUsername())
                .map(ValidatedGetProfileRequest::valueObjectsToDomain);
    }

    private static ValidatedGetProfileRequest valueObjectsToDomain(Pair<Username, Username> it) {
        return new ValidatedGetProfileRequest(it.getValue0(), it.getValue1());
    }

    private static Result<Pair<Username, Username>> wrapPrimitivesToValueObject(String userUsername, String profileUsername) {
        return Result.zip(Username.emerge(userUsername), Username.emerge(profileUsername));
    }
}
