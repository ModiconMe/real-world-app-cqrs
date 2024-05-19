package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateArticleService {

    private final CreateArticleGateway createArticleGateway;

    public Result<CreateArticleResponse> createArticle(CreateArticleRequest request, long userId) {
        return ValidatedCreateArticleRequest.emerge(request, userId)
                .flatMap(createArticleGateway::createArticleAndTags)
                .flatMap(this::mapToResponse);
    }

    private Result<CreateArticleResponse> mapToResponse(CreatedArticle article) {
        return createArticleGateway.findProfile(article.getRequest().getUserId())
                .map(it -> new CreateArticleResponse(
                        new ArticleDto(
                                article.getRequest().getTitle(),
                                article.getRequest().getSlug(),
                                article.getRequest().getDescription(),
                                article.getRequest().getBody(),
                                article.getRequest().getTagList(),
                                article.getCreatedAt(),
                                article.getUpdatedAt(),
                                it
                        )
                ));
    }
}
