package com.modiconme.realworld.domain.profileunfollow;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class ValidatedUnfollowProfileRequest {

    private final UserId userId;
    private final Username profileUsername;

    static Result<ValidatedUnfollowProfileRequest> emerge(UnfollowProfileRequest request) {
        return wrapPrimitivesToValueObjects(request)
                .map(ValidatedUnfollowProfileRequest::tupleToDomain);
    }

    private static Result<Pair<UserId, Username>> wrapPrimitivesToValueObjects(UnfollowProfileRequest request) {
        return Result.zip(
                UserId.emerge(request.currentUserId()),
                Username.emerge(request.profileUsername())
        );
    }

    private static ValidatedUnfollowProfileRequest tupleToDomain(Pair<UserId, Username> zip) {
        return new ValidatedUnfollowProfileRequest(zip.getValue0(), zip.getValue1());
    }
}
