package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaArticleRepositoryAdapter {

    private final DataUserRepository repository;

    
}
