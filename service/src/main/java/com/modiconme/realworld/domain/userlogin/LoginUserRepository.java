package com.modiconme.realworld.domain.userlogin;

import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface LoginUserRepository extends CrudRepository<UserEntity, Long> {
    @Query(value = """
            SELECT id,
                   email,
                   username,
                   password,
                   bio,
                   image
              FROM users
             WHERE email = :email
            """, nativeQuery = true)
    Optional<Tuple> findByEmail(@Param("email") String email);
}
