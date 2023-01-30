package com.modiconme.realworld.rest;

import com.modiconme.realworld.client.ArticleClient;
import com.modiconme.realworld.client.ProfileClient;
import com.modiconme.realworld.command.AddComment;
import com.modiconme.realworld.command.CreateArticle;
import com.modiconme.realworld.command.UpdateArticle;
import com.modiconme.realworld.dto.ArticleDto;
import com.modiconme.realworld.dto.CommentDto;
import com.modiconme.realworld.rest.config.FeignBasedRestTest;
import com.modiconme.realworld.rest.utils.AuthUtils;
import feign.FeignException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class ArticleApiTest extends FeignBasedRestTest {

    public static final String TEST_TITLE = "test-title";
    public static final String TEST_DESCRIPTION = "test-description";
    public static final String TEST_BODY = "test-error";
    public static final String ALTERED_TITLE = "altered-title";
    public static final String ALTERED_BODY = "altered-error";
    public static final String ALTERED_DESCRIPTION = "altered-description";

    @Autowired
    private AuthUtils auth;

    @Autowired
    private ArticleClient articleClient;

    @Autowired
    private ProfileClient profileClient;

    @AfterEach
    void afterEach() {
        auth.logout();
    }

    @Test
    void should_returnCorrectData_whenCreateArticle() {
        auth.register().login();

        CreateArticle cmd = createArticle();
        ArticleDto article = articleClient.createArticle(cmd).getArticle();

        assertThat(article.title()).isEqualTo(cmd.getTitle());
        assertThat(article.description()).isEqualTo(cmd.getDescription());
        assertThat(article.body()).isEqualTo(cmd.getBody());
        assertThat(article.tagList()).isNotEmpty();
        assertThat(article.tagList().size()).isEqualTo(cmd.getTagList().size());
    }

    @Test
    void should_throw422_whenArticleAlreadyExist() {
        auth.register().login();

        CreateArticle cmd = createArticle();
        articleClient.createArticle(cmd);
        FeignException exception = catchThrowableOfType(() -> articleClient.createArticle(cmd), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    void should_throw403_whenCreateArticleWithNoAuth() {
        auth.register();

        CreateArticle cmd = createArticle();
        FeignException exception = catchThrowableOfType(() -> articleClient.createArticle(cmd), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void should_returnCorrectData_whenGetArticleBySlug() {
        auth.register().login();

        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        auth.logout();

        ArticleDto article = articleClient.getArticle(createdArticle.slug()).getArticle();

        assertThat(article.slug()).isEqualTo(createdArticle.slug());
        assertThat(article.title()).isEqualTo(createdArticle.title());
        assertThat(article.description()).isEqualTo(createdArticle.description());
        assertThat(article.body()).isEqualTo(createdArticle.body());
        assertThat(article.tagList()).isNotEmpty();
    }

    @Test
    void should_throw404_whenArticleIsNotExist() {
        auth.register().login();

        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        auth.logout();

        FeignException exception = catchThrowableOfType(() -> articleClient.getArticle(createdArticle.slug() + "ex"), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_returnCorrectData_whenListArticlesNoAuth() {
        auth.register().login();

        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        auth.logout();

        ArticleDto expected = articleClient.getArticle(createdArticle.slug()).getArticle();
        List<ArticleDto> articles = articleClient.listArticles(
                createdArticle.tagList().get(0),
                createdArticle.author().username(),
                null,
                "1",
                "0").getArticles();

        assertThat(articles).isNotEmpty();
    }

    @Test
    void should_returnCorrectData_whenGetFeedAuth() {
        AuthUtils.RegisteredUser user1 = auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        auth.register();

        auth.register().login();
        profileClient.followProfile(user1.getUsername());

        ArticleDto expected = articleClient.getArticle(createdArticle.slug()).getArticle();
        List<ArticleDto> articles = articleClient.feedArticles("1", "0").getArticles();

        assertThat(articles).isNotEmpty();
        assertThat(articles.get(0).slug()).isEqualTo(expected.slug());
    }

    @Test
    void should_throw_whenGetFeedNoAuth() {
        FeignException exception = catchThrowableOfType(() -> articleClient.feedArticles("1", "0"), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void should_returnCorrectData_whenUpdateArticle() {
        auth.register().login();

        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        UpdateArticle updateArticle = new UpdateArticle(
                null, null, ALTERED_TITLE, ALTERED_DESCRIPTION, ALTERED_BODY);
        ArticleDto article = articleClient.updateArticle(createdArticle.slug(), updateArticle).getArticle();

        assertThat(article.title()).isEqualTo(ALTERED_TITLE);
        assertThat(article.description()).isEqualTo(ALTERED_DESCRIPTION);
        assertThat(article.body()).isEqualTo(ALTERED_BODY);
    }

    @Test
    void should_throw401_whenUpdateArticleNoAuth() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        UpdateArticle updateArticle = new UpdateArticle(
                null, null, ALTERED_TITLE, ALTERED_DESCRIPTION, ALTERED_BODY);

        auth.logout();
        FeignException exception = catchThrowableOfType(() -> articleClient.updateArticle(createdArticle.slug(), updateArticle), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void should_throw404_whenUpdateNonExistedArticle() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        UpdateArticle updateArticle = new UpdateArticle(
                null, null, ALTERED_TITLE, ALTERED_DESCRIPTION, ALTERED_BODY);

        FeignException exception = catchThrowableOfType(() -> articleClient.updateArticle(createdArticle.slug() + "non_exist", updateArticle), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_throw422_whenUpdateArticleWithSlugThatAlreadyExist() {
        auth.register().login();
        ArticleDto createdArticle1 = articleClient.createArticle(createArticle()).getArticle();
        ArticleDto createdArticle2 = articleClient.createArticle(createArticle()).getArticle();
        UpdateArticle updateArticle = new UpdateArticle(
                null, null, createdArticle2.title(), ALTERED_DESCRIPTION, ALTERED_BODY);

        FeignException exception = catchThrowableOfType(() -> articleClient.updateArticle(createdArticle1.slug(), updateArticle), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    void should_throw403_whenUpdateArticleNotByOwner() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();

        auth.register().login();

        UpdateArticle updateArticle = new UpdateArticle(
                null, null, ALTERED_TITLE, ALTERED_DESCRIPTION, ALTERED_BODY);

        FeignException exception = catchThrowableOfType(() -> articleClient.updateArticle(createdArticle.slug(), updateArticle), FeignException.class);

        assertThat(exception.status()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void should_returnCorrectData_whenDeleteArticle() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        articleClient.deleteArticle(createdArticle.slug());

        FeignException exception = catchThrowableOfType(() -> articleClient.getArticle(createdArticle.slug()), FeignException.class);
        assertThat(exception.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_throw404_whenDeleteArticleThatNotExist() {
        auth.register().login();
        FeignException exception = catchThrowableOfType(() -> articleClient.deleteArticle(UUID.randomUUID().toString()), FeignException.class);
        assertThat(exception.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_throw403_whenDeleteArticleNotByOwner() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        auth.register().login();
        FeignException exception = catchThrowableOfType(() -> articleClient.deleteArticle(createdArticle.slug()), FeignException.class);
        assertThat(exception.status()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void should_returnCorrectData_whenAddComment() {
        AuthUtils.RegisteredUser author = auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        AddComment cmd = addComment();
        CommentDto addedComment = articleClient.addComment(createdArticle.slug(), cmd).getComment();

        assertThat(addedComment.body()).isEqualTo(cmd.getBody());
        assertThat(addedComment.author().username()).isEqualTo(author.getUsername());
    }

    @Test
    void should_throw401_whenAddCommentNoAuth() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        auth.logout();

        FeignException exception = catchThrowableOfType(() -> articleClient.addComment(createdArticle.slug(), addComment()), FeignException.class);
        assertThat(exception.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void should_throw404_whenAddCommentToNotExistedArticle() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();

        FeignException exception = catchThrowableOfType(() -> articleClient.addComment(createdArticle.slug() + "notexisted", addComment()), FeignException.class);
        assertThat(exception.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_returnCorrectData_whenGetComments() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        CommentDto addedComment = articleClient.addComment(createdArticle.slug(), addComment()).getComment();

        List<CommentDto> comments = articleClient.getComments(createdArticle.slug()).getComments();

        assertThat(comments).isNotEmpty();
        assertThat(comments.get(0).body()).isEqualTo(addedComment.body());
    }

    @Test
    void should_returnCorrectData_whenGetCommentsNoAuth() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        CommentDto addedComment = articleClient.addComment(createdArticle.slug(), addComment()).getComment();
        auth.logout();

        List<CommentDto> comments = articleClient.getComments(createdArticle.slug()).getComments();

        assertThat(comments).isNotEmpty();
        assertThat(comments.get(0).body()).isEqualTo(addedComment.body());
    }

    @Test
    void should_throw404_whenGetCommentsOfNotExistedArticle() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        articleClient.addComment(createdArticle.slug(), addComment());

        FeignException exception = catchThrowableOfType(() -> articleClient.getComments(createdArticle.slug() + "notexisted"), FeignException.class);
        assertThat(exception.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_returnCorrectData_whenFavoriteArticle() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();

        ArticleDto article = articleClient.favoriteArticle(createdArticle.slug()).getArticle();

        assertThat(article.favorited()).isTrue();
        assertThat(article.favoritesCount()).isEqualTo(1);
    }

    @Test
    void should_return401_whenFavoriteArticleNoAuth() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        auth.logout();

        FeignException exception = catchThrowableOfType(() -> articleClient.favoriteArticle(createdArticle.slug()), FeignException.class);
        assertThat(exception.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void should_return404_whenFavoriteArticleThatNotExist() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();

        FeignException exception = catchThrowableOfType(() -> articleClient.favoriteArticle(createdArticle.slug() + "notexisted"), FeignException.class);
        assertThat(exception.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_returnCorrectData_whenUnfavoriteArticle() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        ArticleDto article = articleClient.favoriteArticle(createdArticle.slug()).getArticle();
        assertThat(article.favorited()).isTrue();
        assertThat(article.favoritesCount()).isEqualTo(1);

        article = articleClient.unfavoriteArticle(createdArticle.slug()).getArticle();
        assertThat(article.favorited()).isFalse();
        assertThat(article.favoritesCount()).isEqualTo(0);
    }

    @Test
    void should_return401_whenUnfavoriteArticleNoAuth() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();
        auth.logout();

        FeignException exception = catchThrowableOfType(() -> articleClient.unfavoriteArticle(createdArticle.slug()), FeignException.class);
        assertThat(exception.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void should_return404_whenUnfavoriteArticleThatNotExist() {
        auth.register().login();
        ArticleDto createdArticle = articleClient.createArticle(createArticle()).getArticle();

        FeignException exception = catchThrowableOfType(() -> articleClient.unfavoriteArticle(createdArticle.slug() + "notexisted"), FeignException.class);
        assertThat(exception.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private static CreateArticle createArticle() {
        return new CreateArticle(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        );
    }

    private static AddComment addComment() {
        return new AddComment(null, null, UUID.randomUUID().toString());
    }

}
