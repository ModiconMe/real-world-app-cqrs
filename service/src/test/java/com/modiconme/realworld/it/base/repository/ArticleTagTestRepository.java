package com.modiconme.realworld.it.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author popov.d, E-mail popov.d@soft-logic.ru
 * Created on 19.05.2024
 */
public interface ArticleTagTestRepository extends JpaRepository<ArticleTagEntity, Long> {

    List<ArticleTagEntity> findByArticleId(Long articleId);
}
