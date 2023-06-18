package com.modiconme.realworld.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class FollowRelationId implements Serializable {

    private long idFollowee;
    private long idFollower;

}
