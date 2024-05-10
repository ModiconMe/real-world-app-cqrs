package com.modiconme.realworld.domain.profilefollow;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class ValidatedFollowProfileRequest {

    private final UserId currentUserId;
    private final Username profileUsername;

    static Result<ValidatedFollowProfileRequest> emerge(FollowProfileRequest request) {
        return wrapPrimitivesToValueObjects(request)
                .map(ValidatedFollowProfileRequest::tupleToDomain);
    }

    private static Result<Pair<UserId, Username>> wrapPrimitivesToValueObjects(FollowProfileRequest request) {
        return Result.zip(UserId.emerge(request.currentUserId()), Username.emerge(request.profileUsername()));
    }

    private static ValidatedFollowProfileRequest tupleToDomain(Pair<UserId, Username> zip) {
        return new ValidatedFollowProfileRequest(zip.getValue0(), zip.getValue1());
    }
}
