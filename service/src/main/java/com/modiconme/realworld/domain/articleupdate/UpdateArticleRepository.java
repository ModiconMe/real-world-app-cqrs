package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.articlecreate.AuthorDto;
import com.modiconme.realworld.domain.articlecreate.Slug;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ArticleId;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UpdateArticleRepository {

    private final JdbcClient jdbcClient;

    Optional<Result<ArticleToUpdate>> findArticleToUpdate(Slug slug, UserId userId) {
        String sql = """
                WITH
                article AS (
                    SELECT id,
                           title,
                           description,
                           body,
                           created_at
                      FROM article a
                     WHERE slug = :slug
                       AND id_author = :idAuthor
                ),
                article_favourites AS (
                    SELECT id_user
                      FROM article_user_favorite auf
                     WHERE auf.id_article = (SELECT id
                                              FROM article)
                )
                SELECT a.id,
                       a.title,
                       a.description,
                       a.body,
                       a.created_at,
                       (SELECT COUNT(*)
                          FROM article_favourites) AS favourite_count,
                       (SELECT EXISTS(SELECT 1
                                        FROM article_favourites
                                       WHERE id_user = :idAuthor)) AS favourite_by_user
                  FROM article a
                   FOR UPDATE;
                """;

        return jdbcClient.sql(sql)
                .param("slug", slug.getValue())
                .param("idAuthor", userId.getValue())
                .query((rs, rowNum) -> ArticleToUpdate.emerge(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("body"),
                        rs.getTimestamp("created_at").toInstant(),
                        rs.getLong("favourite_count"),
                        rs.getBoolean("favourite_by_user")))
                .optional();

    }

    UpdatedArticle updateArticle(NewArticle it) {
        String sql = """
                UPDATE article
                   SET slug = :slug,
                       title = :title,
                       description = :description,
                       body = :body,
                       updated_at = NOW()
                 WHERE id = :id
                       RETURNING created_at, updated_at
                """;
        return jdbcClient.sql(sql)
                .param("slug", it.getSlug().getValue())
                .param("title", it.getTitle().getValue())
                .param("description", it.getDescription().getValue())
                .param("body", it.getBody().getValue())
                .param("id", it.getArticleId().getValue())
                .query((rs, rowNum) -> new UpdatedArticle(
                        it.getArticleId(),
                        it.getSlug(),
                        it.getTitle(),
                        it.getDescription(),
                        it.getBody(),
                        rs.getTimestamp("created_at").toInstant(),
                        rs.getTimestamp("updated_at").toInstant(),
                        it.getArticleFavouriteCount(),
                        it.getArticleFavouriteByUser()
                ))
                .single();
    }

    List<String> findArticleTags(ArticleId articleId) {
        String sql = """
                SELECT t.tag_name
                  FROM article_article_tag aat
                       JOIN tag t ON aat.id_tag = t.id
                 WHERE id_article = :idArticle
                """;
        return jdbcClient.sql(sql)
                .param("idArticle", articleId.getValue())
                .query(String.class)
                .list();
    }

    AuthorDto findProfile(long userId) {
        String sql = """
                   SELECT profile.username AS username,
                          profile.bio AS bio,
                          profile.image AS image
                     FROM users profile
                    WHERE profile.id = :id
                """;
        return jdbcClient.sql(sql)
                .param("id", userId)
                .query((rs, rowNum) -> new AuthorDto(
                                rs.getString("username"),
                                rs.getString("bio"),
                                rs.getString("image"),
                                false
                        )
                )
                .single();
    }
}
