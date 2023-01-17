package com.modiconme.realworld.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "id")
@ToString
@Getter
@Setter
@Entity
@Table
public class FollowRelationEntity {

    @EmbeddedId
    private FollowRelationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followerId")
    private UserEntity follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followeeId")
    private UserEntity followee;
}
