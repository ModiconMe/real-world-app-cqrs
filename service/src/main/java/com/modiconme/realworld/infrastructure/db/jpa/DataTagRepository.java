package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.TagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DataTagRepository extends CrudRepository<TagEntity, Long> {

    Optional<TagEntity> findByTagName(String name);

}
