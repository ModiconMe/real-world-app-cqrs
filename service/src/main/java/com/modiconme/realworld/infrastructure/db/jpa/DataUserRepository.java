package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DataUserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findByEmailOrUsername(String email, String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

}
