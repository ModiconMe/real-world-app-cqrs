package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.articlecreate.AuthorDto;
import com.modiconme.realworld.domain.articlecreate.Slug;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ArticleId;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.modiconme.realworld.domain.common.Result.failure;
import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.notFound;

@Slf4j
@Component
@RequiredArgsConstructor
class UpdateArticleGateway {

    private final UpdateArticleRepository repository;

    Result<ArticleToUpdate> findArticleToUpdate(Slug slug, UserId userId) {
        return repository.findArticleToUpdate(slug, userId)
                .orElse(failure(notFound("Article not found by slug %s", slug)));
    }

    Result<UpdatedArticle> updateArticle(NewArticle it) {
        return Result.runCatching(() -> repository.updateArticle(it))
                .onFailure(t -> log.error("Error occurred while update article:", t));
    }

    List<String> findArticleTags(ArticleId articleId) {
        return repository.findArticleTags(articleId);
    }

    AuthorDto findProfile(long userId) {
        return repository.findProfile(userId);
    }
}
