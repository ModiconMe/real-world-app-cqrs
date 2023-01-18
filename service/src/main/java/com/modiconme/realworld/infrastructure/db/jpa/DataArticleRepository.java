package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.TagEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataArticleRepository extends PagingAndSortingRepository<ArticleEntity, UUID> {

    Optional<ArticleEntity> findBySlug(String slug);

//    @Query("SELECT Article a FROM ArticleEntity " +
//            "JOIN a.author author " +
//            "JOIN a.tags t " +
//            "JOIN a.favoriteList fl " +
//            "WHERE" +
//            "(author = NULL OR author = a) AND" +
//            "(tag = NULL OR tag = t) AND" +
//            "(favoritedBy = NULL OR favoritedBy = fl.account) AND" +
//            "(a.favoritedBy)")
//    List<ArticleEntity> findByFilter(
//            TagEntity tag,
//            UserEntity author,
//            UserEntity favoritedBy,
//            Pageable pageable);

}
