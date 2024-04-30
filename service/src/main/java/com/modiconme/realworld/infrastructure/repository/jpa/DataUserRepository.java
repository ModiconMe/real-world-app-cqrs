package com.modiconme.realworld.infrastructure.repository.jpa;

import com.modiconme.realworld.domain.common.UserEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DataUserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    @Query(value = """
                SELECT profile.username AS username,
                       profile.bio AS bio,
                       profile.image AS image,
                       (follower.id IS NOT NULL) AS following
                  FROM users profile
                       LEFT JOIN follow_relation fr ON profile.id = fr.id_followee
                       LEFT JOIN users follower ON fr.id_follower = follower.id
                                               AND follower.username = :currentUsername
                 WHERE profile.username = :profileUsername
            """, nativeQuery = true)
    Optional<Tuple> findProfile(@Param("currentUsername") String currentUsername,
                                @Param("profileUsername") String profileUsername);
}
