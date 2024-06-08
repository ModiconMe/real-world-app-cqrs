package com.modiconme.realworld.it.updatearticle;

import com.modiconme.realworld.domain.articlecreate.CreateArticleRequest;
import com.modiconme.realworld.domain.articlecreate.CreateArticleResponse;
import com.modiconme.realworld.domain.articleupdate.UpdateArticleRequest;
import com.modiconme.realworld.domain.articleupdate.UpdateArticleResponse;
import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.infrastructure.repository.jpa.entity.UserEntity;
import com.modiconme.realworld.infrastructure.web.controller.RestResponse;
import com.modiconme.realworld.it.base.SpringIntegrationTest;
import com.modiconme.realworld.it.base.TestDataGenerator;
import com.modiconme.realworld.it.base.assertions.RestResponseAssertion;
import com.modiconme.realworld.it.base.repository.ArticleEntity;
import com.modiconme.realworld.it.base.repository.ArticleTagEntity;
import com.modiconme.realworld.it.base.repository.TagEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static com.modiconme.realworld.it.base.builder.TagTestBuilder.aTag;
import static com.modiconme.realworld.it.base.builder.UserEntityTestBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class UpdateArticleTest extends SpringIntegrationTest {

    final PasswordEncoder passwordEncoder;
    final TestRestTemplate testRestTemplate;

    @Test
    void success() {
        UserEntity currentUser = db.persisted(aUser(passwordEncoder).build());
        TagEntity existedTag = db.persisted(aTag().build());
        String token = authClient.authenticate(currentUser.getEmail(), "password").token();

        CreateArticleRequest createArticleRequest = new CreateArticleRequest(
                TestDataGenerator.uniqString(), "description", "body", List.of("tag1", existedTag.getTagName())
        );

        ResponseEntity<RestResponse<CreateArticleResponse>> createResult = testRestTemplate.exchange(
                "/api/articles",
                HttpMethod.POST,
                new HttpEntity<>(createArticleRequest, getAuthorizationHeader(token)),
                new ParameterizedTypeReference<>() {
                });

        RestResponseAssertion.assertThat(createResult)
                .hasStatusCodeEquals(HttpStatus.OK)
                .hasErrorEquals(null);

        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(
                TestDataGenerator.uniqString(), "newDescription", "newBody"
        );

        ResponseEntity<RestResponse<UpdateArticleResponse>> result = testRestTemplate.exchange(
                "/api/articles/{slug}",
                HttpMethod.PUT,
                new HttpEntity<>(updateArticleRequest, getAuthorizationHeader(token)),
                new ParameterizedTypeReference<>() {
                }, createArticleRequest.title());
        RestResponseAssertion.assertThat(result)
                .hasStatusCodeEquals(HttpStatus.OK)
                .hasErrorEquals(null);

        assertThat(result.getBody()).isNotNull();
        Optional<ArticleEntity> expectedArticle = db.articles.findBySlug(result.getBody().getData().article().slug());
        assertThat(expectedArticle).isPresent();
        assertThat(result.getBody().getData().article().body()).isEqualTo(expectedArticle.get().getBody());
        assertThat(result.getBody().getData().article().title()).isEqualTo(expectedArticle.get().getTitle());
        assertThat(result.getBody().getData().article().slug()).isEqualTo(expectedArticle.get().getSlug());
        assertThat(result.getBody().getData().article().description()).isEqualTo(expectedArticle.get().getDescription());
        assertThat(result.getBody().getData().article().createdAt()).isEqualTo(result.getBody().getData().article().createdAt());
        assertThat(result.getBody().getData().article().updatedAt()).isEqualTo(result.getBody().getData().article().updatedAt());
        assertThat(result.getBody().getData().article().favorited()).isFalse();
        assertThat(result.getBody().getData().article().favoritesCount()).isZero();

        List<ArticleTagEntity> articleTags = db.articleTags.findByArticleId(expectedArticle.get().getId());
        assertThat(articleTags).hasSize(2);

        Optional<TagEntity> tag1 = db.tags.findById(articleTags.get(0).getTag().getId());
        assertThat(tag1).isPresent();
        assertThat(tag1.get().getTagName()).isEqualTo("tag1");

        Optional<TagEntity> tag2 = db.tags.findById(articleTags.get(1).getTag().getId());
        assertThat(tag2).contains(existedTag);
    }

}
