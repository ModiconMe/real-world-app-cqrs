package com.modiconme.realworld.domain.profileget;

import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface GetProfileRepository extends CrudRepository<UserEntity, Long> {

    @Query(value = """
            SELECT profile.username AS username,
                   profile.bio AS bio,
                   profile.image AS image,
                   (SELECT EXISTS(SELECT 1
                                    FROM follow_relation fr
                                   WHERE fr.id_followee = profile.id
                                     AND fr.id_follower = :currentUserId)) AS following
              FROM users profile
             WHERE profile.username = :profileUsername""", nativeQuery = true)
    Optional<Tuple> findProfile(@Param("currentUserId") long currentUserId,
                                @Param("profileUsername") String profileUsername);
}
