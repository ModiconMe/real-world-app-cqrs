package com.modiconme.realworld.domain.profileunfollow;

import com.modiconme.realworld.infrastructure.repository.jpa.entity.FollowRelationEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface UnfollowProfileRepository extends JpaRepository<FollowRelationEntity, Long> {

    @Query(value = """
                SELECT profile.id,
                       profile.username,
                       profile.bio,
                       profile.image,
                       fr.id AS follow_relation_id
                  FROM users profile
                       JOIN follow_relation fr ON profile.id = fr.id_followee AND fr.id_follower = :followedBy
                 WHERE profile.username = :profileUsername
            """, nativeQuery = true)
    Optional<Tuple> findFollowedProfile(@Param("followedBy") long followedBy,
                                        @Param("profileUsername") String profileUsername);
}
