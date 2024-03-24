package com.modiconme.realworld.handlers.query;

import com.modiconme.realworld.application.query.GetArticlesHandler;
import com.modiconme.realworld.core.BaseTest;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.TagEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.ArticleDto;
import com.modiconme.realworld.query.GetArticles;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.modiconme.realworld.core.data.var2.GenericBuilder.uniqString;
import static com.modiconme.realworld.core.data.var2.GenericBuilderDsl.*;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GetArticlesHandlerTest extends BaseTest {

    private final GetArticlesHandler handler;

    @Test
    void shouldGetArticlesWithLimitAndOffset() {
        // given
        UserEntity author1 = given(user());

        TagEntity tag1 = db.persisted(new TagEntity(uniqString()));
        TagEntity tag2 = db.persisted(new TagEntity(uniqString()));

        UserEntity user1 = given(user(), db);
        UserEntity user2 = given(user());
        db.persisted(user2.followUser(author1));

        given(article(author1).tag(tag1).favoriteBy(user1).favoriteBy(user2), db);
        ArticleEntity article2 = given(article(author1).tag(tag2).favoriteBy(user1), db);
        ArticleEntity article3 = given(article(author1).tag(tag1).tag(tag2).favoriteBy(user1).favoriteBy(user2), db);

        var request = new GetArticles(null, author1.getUsername(), null,
                "2", "1", user2.getUsername());

        // when
        List<ArticleDto> response = handler.handle(request).getArticles();

        // then
        assertThat(response).hasSize(2);
        assertThat(response.get(0).slug()).isEqualTo(article2.getSlug());
        assertThat(response.get(0).tagList()).hasSize(1);
        assertThat(response.get(0).author().following()).isTrue();
        assertThat(response.get(0).favoritesCount()).isEqualTo(1);
        assertThat(response.get(1).slug()).isEqualTo(article3.getSlug());
        assertThat(response.get(1).tagList()).hasSize(2);
        assertThat(response.get(1).author().following()).isTrue();
        assertThat(response.get(1).favoritesCount()).isEqualTo(2);
    }

    @Test
    void shouldGetArticlesWithTag() {
        // given
        UserEntity author1 = given(user());
        UserEntity author2 = given(user(), db);

        TagEntity tag1 = db.persisted(new TagEntity(uniqString()));
        TagEntity tag2 = db.persisted(new TagEntity(uniqString()));

        UserEntity user1 = given(user(), db);
        UserEntity user2 = given(user());
        db.persisted(user2.followUser(author1));

        ArticleEntity article1 = given(article(author1).tag(tag1).favoriteBy(user1).favoriteBy(user2), db);
        given(article(author1).tag(tag2).favoriteBy(user1), db);
        ArticleEntity article3 = given(article(author2).tag(tag1).tag(tag2).favoriteBy(user1).favoriteBy(user2), db);

        var request = new GetArticles(tag1.getTagName(), null, null,
                "2", "0", user2.getUsername());

        // when
        List<ArticleDto> response = handler.handle(request).getArticles();

        // then
        assertThat(response).hasSize(2);
        assertThat(response.get(0).slug()).isEqualTo(article1.getSlug());
        assertThat(response.get(0).tagList()).hasSize(1);
        assertThat(response.get(0).author().following()).isTrue();
        assertThat(response.get(0).favoritesCount()).isEqualTo(2);
        assertThat(response.get(1).slug()).isEqualTo(article3.getSlug());
        assertThat(response.get(1).tagList()).hasSize(2);
        assertThat(response.get(1).author().following()).isFalse();
        assertThat(response.get(1).favoritesCount()).isEqualTo(2);
    }

    @Test
    void shouldGetArticlesWithAuthor() {
        // given
        UserEntity author1 = given(user());
        UserEntity author2 = given(user(), db);

        TagEntity tag1 = db.persisted(new TagEntity(uniqString()));
        TagEntity tag2 = db.persisted(new TagEntity(uniqString()));

        UserEntity user1 = given(user(), db);
        UserEntity user2 = given(user());
        db.persisted(user2.followUser(author1));

        ArticleEntity article1 = given(article(author1).tag(tag1).favoriteBy(user1).favoriteBy(user2), db);
        ArticleEntity article2 = given(article(author1).tag(tag2).favoriteBy(user1), db);
        given(article(author2).tag(tag1).tag(tag2).favoriteBy(user1).favoriteBy(user2), db);

        var request = new GetArticles(null, author1.getUsername(), null,
                "2", "0", user2.getUsername());

        // when
        List<ArticleDto> response = handler.handle(request).getArticles();

        // then
        assertThat(response).hasSize(2);
        assertThat(response.get(0).slug()).isEqualTo(article1.getSlug());
        assertThat(response.get(0).tagList()).hasSize(1);
        assertThat(response.get(0).author().following()).isTrue();
        assertThat(response.get(0).favoritesCount()).isEqualTo(2);
        assertThat(response.get(1).slug()).isEqualTo(article2.getSlug());
        assertThat(response.get(1).tagList()).hasSize(1);
        assertThat(response.get(1).author().following()).isTrue();
        assertThat(response.get(1).favoritesCount()).isEqualTo(1);
    }

    @Test
    void shouldGetArticlesWithFavoriteBy() {
        // given
        UserEntity author1 = given(user());
        UserEntity author2 = given(user(), db);

        TagEntity tag1 = db.persisted(new TagEntity(uniqString()));
        TagEntity tag2 = db.persisted(new TagEntity(uniqString()));

        UserEntity user1 = given(user(), db);
        UserEntity user2 = given(user());
        db.persisted(user2.followUser(author1));

        ArticleEntity article1 = given(article(author1).tag(tag1).favoriteBy(user1).favoriteBy(user2), db);
        ArticleEntity article2 = given(article(author1).tag(tag2).favoriteBy(user1), db);
        given(article(author2).tag(tag1).tag(tag2).favoriteBy(user1).favoriteBy(user2), db);

        var request = new GetArticles(null, null, user1.getUsername(),
                "2", "0", user2.getUsername());

        // when
        List<ArticleDto> response = handler.handle(request).getArticles();

        // then
        assertThat(response).hasSize(2);
        assertThat(response.get(0).slug()).isEqualTo(article1.getSlug());
        assertThat(response.get(0).tagList()).hasSize(1);
        assertThat(response.get(0).author().following()).isTrue();
        assertThat(response.get(0).favoritesCount()).isEqualTo(2);
        assertThat(response.get(1).slug()).isEqualTo(article2.getSlug());
        assertThat(response.get(1).tagList()).hasSize(1);
        assertThat(response.get(1).author().following()).isTrue();
        assertThat(response.get(1).favoritesCount()).isEqualTo(1);
    }
}
