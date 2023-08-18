package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataTagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByTagName(String name);

}
