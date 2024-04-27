package com.modiconme.realworld.domain.followprofile;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class FollowerAndFolloweeProfiles {

    private final UserEntity followee;
    private final UserEntity follower;

    public static Result<FollowerAndFolloweeProfiles> emerge(Result<UserEntity> followee,
                                                             Result<UserEntity> follower) {
        Result<Pair<UserEntity, UserEntity>> zip = Result.zip(followee, follower);
        if (zip.isFailure()) {
            return Result.failure(zip.getError());
        }

        return Result.success(new FollowerAndFolloweeProfiles(zip.getData().getValue0(),
                zip.getData().getValue1()));
    }
}
