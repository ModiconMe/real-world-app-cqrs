package com.modiconme.realworld.core.data.var1;

import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.FollowRelationId;
import com.modiconme.realworld.domain.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@NoArgsConstructor(staticName = "aFollowRelation")
@AllArgsConstructor
@With
public class FollowRelationDataBuilder implements TestDataBuilder<FollowRelationEntity> {

    private FollowRelationId id;
    private UserEntity followee;
    private UserEntity follower;

    @Override
    public FollowRelationEntity build() {
        return new FollowRelationEntity(id, followee, follower);
    }
}
