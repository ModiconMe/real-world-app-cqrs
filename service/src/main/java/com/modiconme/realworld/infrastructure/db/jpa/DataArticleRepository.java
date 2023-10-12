package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.ArticleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataArticleRepository extends CrudRepository<ArticleEntity, UUID> {

    Optional<ArticleEntity> findBySlugIgnoreCaseContaining(String slug);

    Optional<ArticleEntity> findBySlugIgnoreCase(String slug);

    @Query("""
            SELECT DISTINCT ar FROM ArticleEntity ar
                   LEFT JOIN ar.author a
                   LEFT JOIN ar.tags t
                   LEFT JOIN ar.favoriteList f
             WHERE (:author IS NULL OR a.username = :author)
               AND (:tag IS NULL OR t.tagName = :tag)
               AND (:favoritedBy IS NULL OR f.username = :favoritedBy)""")
    List<ArticleEntity> findByFilter(
            @Param("tag") String tag,
            @Param("author") String author,
            @Param("favoritedBy") String favoritedBy,
            Pageable pageable);

//    @Query("SELECT DISTINCT ar FROM ArticleEntity ar WHERE ar.author IN :followees")
//    List<ArticleEntity> findByFeed(
//            @Param("followees") List<UUID> followees,
//            Pageable pageable);

    @Query("SELECT DISTINCT ar FROM ArticleEntity ar " +
            "LEFT JOIN ar.author ac " +
            "LEFT JOIN ac.followers fe " +
            "WHERE " +
            "(fe.follower.username = :username)")
    List<ArticleEntity> findByFeed(
            @Param("username") String username,
            Pageable pageable);

}
