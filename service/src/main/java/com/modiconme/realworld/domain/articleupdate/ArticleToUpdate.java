package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.articlecreate.Body;
import com.modiconme.realworld.domain.articlecreate.Description;
import com.modiconme.realworld.domain.articlecreate.Title;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.ArticleId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Quintet;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author popov.d, E-mail popov.d@soft-logic.ru
 * Created on 21.05.2024
 */
@Getter
@RequiredArgsConstructor(access = PRIVATE)
public final class ArticleToUpdate {

    private final ArticleId articleId;
    private final Title title;
    private final Description description;
    private final Body body;
    private final Instant createdAt;
    private final ArticleFavouriteCount articleFavouriteCount;
    private final ArticleFavouriteByUser articleFavouriteByUser;

    static Result<ArticleToUpdate> emerge(
            long articleId,
            String title,
            String description,
            String body,
            Instant createdAt,
            long favouriteCount,
            boolean articleFavouriteByUser
    ) {
        return wrapPrimitivesToValueObject(articleId, title, description, body, favouriteCount)
                .map(it -> mapToDomainObject(it, createdAt, articleFavouriteByUser));
    }

    private static Result<Quintet<ArticleId, Title, Description, Body, ArticleFavouriteCount>> wrapPrimitivesToValueObject(
            long articleId,
            String title,
            String description,
            String body,
            long favouriteCount
    ) {
        return Result.zip(
                ArticleId.emerge(articleId),
                Title.emerge(title),
                Description.emerge(description),
                Body.emerge(body),
                ArticleFavouriteCount.emerge(favouriteCount)
        );
    }

    private static ArticleToUpdate mapToDomainObject(
            Quintet<ArticleId, Title, Description, Body, ArticleFavouriteCount> it,
            Instant createdAt,
            boolean articleFavouriteByUser
    ) {
        return new ArticleToUpdate(
                it.getValue0(),
                it.getValue1(),
                it.getValue2(),
                it.getValue3(),
                createdAt,
                it.getValue4(),
                ArticleFavouriteByUser.of(articleFavouriteByUser)
        );
    }

}
