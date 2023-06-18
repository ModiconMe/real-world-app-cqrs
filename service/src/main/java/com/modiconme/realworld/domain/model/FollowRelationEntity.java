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
@Table(name = "follow_relation")
public class FollowRelationEntity {

    @EmbeddedId
    private FollowRelationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_followee")
    @MapsId("idFollowee")
    private UserEntity followee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_follower")
    @MapsId("idFollower")
    private UserEntity follower;
}
