package com.modiconme.realworld.domain.profilefollow;

import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface FollowProfileRepository extends CrudRepository<UserEntity, Long> {

    @Query(value = """
                SELECT profile.id,
                       profile.username,
                       profile.bio,
                       profile.image
                  FROM users profile
                 WHERE profile.username = :profileUsername
                   AND NOT EXISTS(SELECT 1
                                    FROM follow_relation fr
                                   WHERE fr.id_followee = profile.id
                                     AND fr.id_follower = :followedById)
            """, nativeQuery = true)
    Optional<Tuple> findUnfollowedProfile(@Param("followedById") long followedById,
                                          @Param("profileUsername") String profileUsername);

    @Query(value = """
            INSERT INTO follow_relation (id_follower, id_followee)
            VALUES (:followerId, :followeeId)
                   RETURNING id;
            """, nativeQuery = true)
    long upsert(@Param("followerId") long followerId,
                @Param("followeeId") long followeeId)
            throws PersistenceException;
}
