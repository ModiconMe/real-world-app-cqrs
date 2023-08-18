package com.modiconme.realworld.domain.repository;

import com.modiconme.realworld.domain.model.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    List<TagEntity> findAll();

    Optional<TagEntity> findByTagName(String name);

    TagEntity save(TagEntity tagEntity);

}
