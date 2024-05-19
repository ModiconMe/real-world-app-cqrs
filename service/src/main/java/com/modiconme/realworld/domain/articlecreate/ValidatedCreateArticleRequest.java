package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javatuples.Sextet;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
class ValidatedCreateArticleRequest {

    private final UserId userId;
    private final Title title;
    private final Slug slug;
    private final Description description;
    private final Body body;
    private final TagList tagList;

    static Result<ValidatedCreateArticleRequest> emerge(CreateArticleRequest request, long userId) {
        return wrapPrimitivesToValueObject(request, userId)
                .map(ValidatedCreateArticleRequest::mapToDomainObject);
    }

    private static Result<Sextet<UserId, Title, Slug, Description, Body, TagList>> wrapPrimitivesToValueObject(
            CreateArticleRequest request, long userId
    ) {
        Result<Title> title = Title.emerge(request.title());
        return Result.zip(
                UserId.emerge(userId),
                title,
                title.flatMap(Slug::emerge),
                Description.emerge(request.description()),
                Body.emerge(request.body()),
                TagList.emerge(request.tagList())
        );
    }

    private static ValidatedCreateArticleRequest mapToDomainObject(
            Sextet<UserId, Title, Slug, Description, Body, TagList> it
    ) {
        return new ValidatedCreateArticleRequest(
                it.getValue0(),
                it.getValue1(),
                it.getValue2(),
                it.getValue3(),
                it.getValue4(),
                it.getValue5()
        );
    }

}
