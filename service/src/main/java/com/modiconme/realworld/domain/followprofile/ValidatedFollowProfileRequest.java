package com.modiconme.realworld.domain.followprofile;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class ValidatedFollowProfileRequest {

    private final Username currentUsername;
    private final Username profileUsername;

    public static Result<ValidatedFollowProfileRequest> emerge(
            UnvalidatedFollowProfileRequest request) {
        return wrapPrimitivesToValueObjects(request)
                .map(ValidatedFollowProfileRequest::tupleToValidatedFollowProfileRequest);
    }

    private static Result<Pair<Username, Username>> wrapPrimitivesToValueObjects(UnvalidatedFollowProfileRequest request) {
        return Result.zip(Username.emerge(request.currentUsername()), Username.emerge(request.profileUsername()));
    }

    private static ValidatedFollowProfileRequest tupleToValidatedFollowProfileRequest(Pair<Username, Username> zip) {
        return new ValidatedFollowProfileRequest(zip.getValue0(), zip.getValue1());
    }
}
