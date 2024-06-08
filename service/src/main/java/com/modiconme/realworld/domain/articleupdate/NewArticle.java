package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.articlecreate.Body;
import com.modiconme.realworld.domain.articlecreate.Description;
import com.modiconme.realworld.domain.articlecreate.Slug;
import com.modiconme.realworld.domain.articlecreate.Title;
import com.modiconme.realworld.domain.common.valueobjects.ArticleId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
final class NewArticle {

    private final ArticleId articleId;
    private final Slug slug;
    private final Title title;
    private final Description description;
    private final Body body;
    private final ArticleFavouriteCount articleFavouriteCount;
    private final ArticleFavouriteByUser articleFavouriteByUser;

    static NewArticle of(
            ValidatedUpdateArticleRequest request, ArticleToUpdate articleToUpdate
    ) {
        return new NewArticle(
                articleToUpdate.getArticleId(),
                Slug.emerge(request.getTitle().getValue().orElse(articleToUpdate.getTitle())).getData(),
                request.getTitle().getValue().orElse(articleToUpdate.getTitle()),
                request.getDescription().getValue().orElse(articleToUpdate.getDescription()),
                request.getBody().getValue().orElse(articleToUpdate.getBody()),
                articleToUpdate.getArticleFavouriteCount(),
                articleToUpdate.getArticleFavouriteByUser()
        );
    }

}
