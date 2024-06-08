package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author popov.d, E-mail popov.d@soft-logic.ru
 * Created on 20.05.2024
 */
@Service
@RequiredArgsConstructor
public class UpdateArticleService {

    private final UpdateArticleGateway updateArticleGateway;

    @Transactional
    public Result<UpdateArticleResponse> updateArticle(UpdateArticleRequest request, String slug, long userId) {
        return ValidatedUpdateArticleRequest.emerge(request, slug, userId)
                .flatMap(this::getArticleToUpdate)
                .flatMap(updateArticleGateway::updateArticle)
                .map(it -> mapToResponse(it, userId));
    }

    private Result<NewArticle> getArticleToUpdate(ValidatedUpdateArticleRequest request) {
        return updateArticleGateway.findArticleToUpdate(request.getSlug(), request.getUserId())
                .map(it -> NewArticle.of(request, it));
    }

    private UpdateArticleResponse mapToResponse(UpdatedArticle it, long userId) {
        List<String> tags = updateArticleGateway.findArticleTags(it.articleId());

        return new UpdateArticleResponse(
                new ArticleDto(
                        it.title(),
                        it.slug(),
                        it.description(),
                        it.body(),
                        tags,
                        it.createdAt(),
                        it.updatedAt(),
                        it.articleFavouriteByUser(),
                        it.articleFavouriteCount(),
                        updateArticleGateway.findProfile(userId)
                )
        );
    }
}
