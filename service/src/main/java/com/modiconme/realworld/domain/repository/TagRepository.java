package com.modiconme.realworld.domain.repository;

import com.modiconme.realworld.domain.model.TagEntity;

import java.util.Optional;

public interface TagRepository {

    Iterable<TagEntity> findAll();

    Optional<TagEntity> findByTagName(String name);

}
