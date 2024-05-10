package com.modiconme.realworld.domain.userregister;

import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

interface RegisterUserRepository extends CrudRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
}
