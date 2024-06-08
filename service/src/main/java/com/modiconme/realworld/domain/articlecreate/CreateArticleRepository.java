package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.valueobjects.ArticleId;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class CreateArticleRepository {

    private static final RowMapper<SavedArticle> SAVED_ARTICLE_ROW_MAPPER = (rs, rowNum) -> new SavedArticle(
            rs.getLong("id"),
            rs.getTimestamp("created_at").toInstant(),
            rs.getTimestamp("updated_at").toInstant()
    );

    private static final RowMapper<AuthorDto> AUTHOR_DTO_ROW_MAPPER = (rs, rowNum) -> new AuthorDto(
            rs.getString("username"),
            rs.getString("bio"),
            rs.getString("image")
    );

    private final JdbcClient jdbcClient;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    SavedArticle saveArticle(
            Title title,
            Slug slug,
            Description description,
            Body body,
            UserId userId
    ) {
        String sql = """
                    INSERT INTO article (title, slug, description, body, id_author)
                    VALUES (:title, :slug, :description, :body, :idAuthor)
                           RETURNING article.*
                """;

        return jdbcClient.sql(sql)
                .param("title", title.getValue())
                .param("slug", slug.getValue())
                .param("description", description.getValue())
                .param("body", body.getValue())
                .param("idAuthor", userId.getValue())
                .query(SAVED_ARTICLE_ROW_MAPPER)
                .single();
    }

    void saveTags(ArticleId articleId, TagList tagList) {
        String sql = """
                    WITH new_tag AS (
                        INSERT INTO tag (tag_name)
                        VALUES (:tagName)
                            ON CONFLICT (tag_name) DO NOTHING
                               RETURNING id
                    )
                    INSERT INTO article_article_tag (id_article, id_tag)
                    SELECT :articleId, (SELECT id FROM new_tag
                                        UNION ALL
                                        SELECT id FROM tag WHERE tag_name = :tagName)
                """;

        var params = tagList.getValue().stream()
                .map(it -> new MapSqlParameterSource("articleId", articleId.getValue()).addValue("tagName", it))
                .toArray(MapSqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(sql, params);
    }

    Optional<AuthorDto> findAuthorById(UserId authorId) {
        String sql = """
                    SELECT username,
                           bio,
                           image
                      FROM users
                     WHERE id = :authorId
                """;

        return jdbcClient.sql(sql)
                .param("authorId", authorId.getValue())
                .query(AUTHOR_DTO_ROW_MAPPER)
                .optional();
    }

    record SavedArticle(long id, Instant createdAt, Instant updatedAt) {
    }

}
