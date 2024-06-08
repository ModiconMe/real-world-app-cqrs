package com.modiconme.realworld.domain.articleupdate;

import com.modiconme.realworld.domain.articlecreate.Slug;
import com.modiconme.realworld.domain.articlecreate.Title;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Sextet;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
class ValidatedUpdateArticleRequest {

    private final UserId userId;
    private final Slug slug;
    private final NewSlug newSlug;
    private final NewTitle title;
    private final NewDescription description;
    private final NewBody body;

    static Result<ValidatedUpdateArticleRequest> emerge(UpdateArticleRequest request, String slug, long userId) {
        return wrapPrimitivesToValueObject(request, slug, userId)
                .map(ValidatedUpdateArticleRequest::mapToDomainObject);
    }

    private static Result<Sextet<UserId, Slug, NewSlug, NewTitle, NewDescription, NewBody>> wrapPrimitivesToValueObject(
            UpdateArticleRequest request, String slug, long userId
    ) {
        return Result.zip(
                UserId.emerge(userId),
                Title.emerge(slug).flatMap(Slug::emerge),
                NewSlug.emerge(request.title()),
                NewTitle.emerge(request.title()),
                NewDescription.emerge(request.description()),
                NewBody.emerge(request.body())
        );
    }

    private static ValidatedUpdateArticleRequest mapToDomainObject(
            Sextet<UserId, Slug, NewSlug, NewTitle, NewDescription, NewBody> it
    ) {
        return new ValidatedUpdateArticleRequest(
                it.getValue0(),
                it.getValue1(),
                it.getValue2(),
                it.getValue3(),
                it.getValue4(),
                it.getValue5()
        );
    }

}
