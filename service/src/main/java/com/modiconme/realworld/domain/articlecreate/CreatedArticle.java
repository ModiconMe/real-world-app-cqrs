package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ArticleId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class CreatedArticle {

    private final ArticleId articleId;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final ValidatedCreateArticleRequest request;

    static Result<CreatedArticle> emerge(long articleId,
                                         Instant createdAt,
                                         Instant updatedAt,
                                         ValidatedCreateArticleRequest request) {
        return wrapPrimitivesToValueObjects(articleId)
                .map(it -> new CreatedArticle(it, createdAt, updatedAt, request));
    }

    private static Result<ArticleId> wrapPrimitivesToValueObjects(long articleId) {
        return ArticleId.emerge(articleId);
    }

}
