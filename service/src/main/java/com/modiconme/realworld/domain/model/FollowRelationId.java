package com.modiconme.realworld.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@EqualsAndHashCode
@Embeddable
public class FollowRelationId implements Serializable {

//    @Serial
//    private static final long serialVersionUID = -3900452076478654225L;

    private long idFollowee;
    private long idFollower;

}
