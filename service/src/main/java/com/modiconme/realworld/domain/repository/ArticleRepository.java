package com.modiconme.realworld.domain.repository;

import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository {

    Optional<ArticleEntity> findBySlug(String slug);

    Optional<ArticleEntity> findBySlugSearch(String slug);

    ArticleEntity save(ArticleEntity article);

    List<ArticleEntity> findByFilters(String tag, String author, String favoritedBy, String offset, String limit);

    List<ArticleEntity> findByFeed(String username, String offset, String limit);

    void delete(ArticleEntity article);
}
