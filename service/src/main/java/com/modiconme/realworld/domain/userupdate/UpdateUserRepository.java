package com.modiconme.realworld.domain.userupdate;

import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface UpdateUserRepository extends CrudRepository<UserEntity, Long> {
    @Query(value = """
            SELECT id,
                   email,
                   username,
                   password,
                   bio,
                   image
              FROM users
             WHERE id = :id
            """, nativeQuery = true)
    Optional<Tuple> nativeFindById(@Param("id") long id);

    @Query(value = """
            SELECT EXISTS (
                        SELECT 1
                          FROM users
                         WHERE id != :id
                           AND email = :email
                   ) AS email_exists,
                   EXISTS (
                        SELECT 1
                          FROM users
                         WHERE id != :id
                           AND username = :username
                   ) AS username_exists
            """, nativeQuery = true)
    Tuple checkUsernameAndEmailAvailable(
            @Param("id") long id,
            @Param("email") String email,
            @Param("username") String username
    );

    @Modifying
    @Query(value = """
            UPDATE users
               SET email = :email,
                   username = :username,
                   password = :password,
                   bio = :bio,
                   image = :image
             WHERE id = :id
            """, nativeQuery = true)
    int updateUser(
            @Param("id") long id,
            @Param("email") String email,
            @Param("username") String username,
            @Param("password") String password,
            @Param("bio") String bio,
            @Param("image") String image
    ) throws PersistenceException;

}
