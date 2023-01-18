package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.TagEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.TagRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaTagRepositoryAdapter implements TagRepository {

    private final DataTagRepository repository;

    @Override
    public Iterable<TagEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TagEntity> findByTagName(String name) {
        return repository.findByTagName(name);
    }
    
}
