package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.FollowRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JpaFollowRelationRepositoryAdapter implements FollowRelationRepository {

    private final DataFollowRelationRepository repository;

    @Override
    public List<FollowRelationEntity> findByFollower(UserEntity user) {
        return repository.findByFollower(user);
    }
}
