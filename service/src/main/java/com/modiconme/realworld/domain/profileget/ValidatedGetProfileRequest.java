package com.modiconme.realworld.domain.profileget;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import com.modiconme.realworld.domain.common.valueobjects.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class ValidatedGetProfileRequest {

    private final UserId currentUserId;
    private final Username profileUsername;

    static Result<ValidatedGetProfileRequest> emerge(GetProfileRequest request) {
        return wrapPrimitivesToValueObject(request.currentUserId(), request.profileUsername())
                .map(ValidatedGetProfileRequest::tupleToDomain);
    }

    private static Result<Pair<UserId, Username>> wrapPrimitivesToValueObject(
            long currentUserId, String profileUsername
    ) {
        return Result.zip(UserId.emerge(currentUserId), Username.emerge(profileUsername));
    }

    private static ValidatedGetProfileRequest tupleToDomain(Pair<UserId, Username> it) {
        return new ValidatedGetProfileRequest(it.getValue0(), it.getValue1());
    }
}
