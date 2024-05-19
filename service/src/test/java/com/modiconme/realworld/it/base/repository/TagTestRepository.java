package com.modiconme.realworld.it.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author popov.d, E-mail popov.d@soft-logic.ru
 * Created on 19.05.2024
 */
public interface TagTestRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByTagName(String tagName);
}
