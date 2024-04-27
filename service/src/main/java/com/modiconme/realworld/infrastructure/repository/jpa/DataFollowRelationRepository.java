package com.modiconme.realworld.infrastructure.repository.jpa;

import com.modiconme.realworld.domain.common.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DataFollowRelationRepository extends JpaRepository<UserEntity, Long> {


    @Modifying
    @Query(value = """
            INSERT INTO follow_relation (id_follower, id_followee)
            VALUES (:followerId, :followeeId)
                ON CONFLICT DO NOTHING;
            """, nativeQuery = true)
    int upsert(@Param("followerId") long followerId, @Param("followeeId") long followeeId);
}
