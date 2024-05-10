package com.modiconme.realworld.infrastructure.security;

import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface SecutiryRepository extends CrudRepository<UserEntity, Long> {

    @Query(value = """
            SELECT id,
                   email,
                   username,
                   password
              FROM users
             WHERE username = :username
            """, nativeQuery = true)
    Optional<Tuple> findByUsername(@Param("username") String username);
}
