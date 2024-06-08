package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.articlecreate.Body;
import com.modiconme.realworld.domain.articlecreate.Description;
import com.modiconme.realworld.domain.articlecreate.Slug;
import com.modiconme.realworld.domain.articlecreate.Title;
import com.modiconme.realworld.domain.common.valueobjects.ArticleId;

import java.time.Instant;

record UpdatedArticle(
        ArticleId articleId,
        Slug slug,
        Title title,
        Description description,
        Body body,
        Instant createdAt,
        Instant updatedAt,
        ArticleFavouriteCount articleFavouriteCount,
        ArticleFavouriteByUser articleFavouriteByUser
) {
}
