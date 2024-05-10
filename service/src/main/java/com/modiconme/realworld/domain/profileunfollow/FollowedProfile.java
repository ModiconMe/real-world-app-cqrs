package com.modiconme.realworld.domain.profileunfollow;

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
final class FollowedProfile {

    private final FolloweeId id;
    private final Username username;
    private final Bio bio;
    private final Image image;
    private final FollowRelationId followRelationId;

    static Result<FollowedProfile> emerge(
            long id, String username, String bio, String image, long followRelationId
    ) {
        return wrapPrimitivesToValueObjects(id, username, bio, image, followRelationId)
                .map(FollowedProfile::mapToDomain);
    }

    private static Result<Quintet<FolloweeId, Username, Bio, Image, FollowRelationId>> wrapPrimitivesToValueObjects(
            long id, String username, String bio, String image, long followRelationId
    ) {
        return Result.zip(
                FolloweeId.emerge(id),
                Username.emerge(username),
                Bio.emerge(bio),
                Image.emerge(image),
                FollowRelationId.emerge(followRelationId)
        );
    }

    private static FollowedProfile mapToDomain(
            Quintet<FolloweeId, Username, Bio, Image, FollowRelationId> tuple
    ) {
        return new FollowedProfile(
                tuple.getValue0(),
                tuple.getValue1(),
                tuple.getValue2(),
                tuple.getValue3(),
                tuple.getValue4()
        );
    }
}
