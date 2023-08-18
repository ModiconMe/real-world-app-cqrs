package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaUserRepositoryAdapter implements UserRepository {

    private final DataUserRepository repository;

    @Override
    public List<UserEntity> findByEmailOrUsername(String email, String username) {
        return repository.findByEmailOrUsername(email, username);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }
    
}
