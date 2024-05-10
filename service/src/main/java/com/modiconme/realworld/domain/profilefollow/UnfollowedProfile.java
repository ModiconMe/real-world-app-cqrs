package com.modiconme.realworld.domain.profilefollow;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.javatuples.Quintet;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
final class UnfollowedProfile {

    private final FolloweeId followeeId;
    private final FollowerId followerId;
    private final Username username;
    private final Bio bio;
    private final Image image;

    static Result<UnfollowedProfile> emerge(long followeeId, UserId followerId, String username, String bio, String image) {
        return wrapPrimitivesToTuple(followeeId, followerId, username, bio, image)
                .map(UnfollowedProfile::mapToDomain);
    }

    private static Result<Quintet<FolloweeId, FollowerId, Username, Bio, Image>> wrapPrimitivesToTuple(
            long id, UserId userId, String username, String bio, String image
    ) {
        return Result.zip(
                FolloweeId.emerge(id),
                FollowerId.emerge(userId.getValue()),
                Username.emerge(username),
                Bio.emerge(bio),
                Image.emerge(image)
        );
    }

    private static UnfollowedProfile mapToDomain(Quintet<FolloweeId, FollowerId, Username, Bio, Image> tuple) {
        return new UnfollowedProfile(
                tuple.getValue0(),
                tuple.getValue1(),
                tuple.getValue2(),
                tuple.getValue3(),
                tuple.getValue4()
        );
    }
}
