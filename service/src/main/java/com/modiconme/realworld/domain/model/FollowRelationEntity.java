package com.modiconme.realworld.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString
@Getter
@Setter
@Entity
@Table(name = "follow_relation")
public class FollowRelationEntity {

    @EmbeddedId
    private FollowRelationId id;

    @ManyToOne
    @JoinColumn(name="id_followee", nullable = false, foreignKey = @ForeignKey(name = "id_followee_fk"))
    @MapsId("idFollowee")
    private UserEntity followee;

    @ManyToOne
    @JoinColumn(name="id_follower", nullable = false, foreignKey = @ForeignKey(name = "id_follower_fk"))
    @MapsId("idFollower")
    private UserEntity follower;
}
