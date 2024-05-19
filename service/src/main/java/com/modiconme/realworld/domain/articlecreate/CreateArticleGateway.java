package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.valueobjects.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.notFound;

@Slf4j
@Component
@RequiredArgsConstructor
class CreateArticleGateway {

    private final CreateArticleRepository repository;

    Result<CreatedArticle> createArticleAndTags(ValidatedCreateArticleRequest request) {
        return Result.runCatching(
                        () -> repository.saveArticle(
                                request.getTitle(),
                                request.getSlug(),
                                request.getDescription(),
                                request.getBody(),
                                request.getUserId()),
                        t -> log.error("Error occurred while save article: ", t)
                )
                .flatMap(it -> CreatedArticle.emerge(it.id(), it.createdAt(), it.updatedAt(), request))
                .tryOnSuccess(
                        it -> repository.saveTags(it.getArticleId(), it.getRequest().getTagList()),
                        t -> log.error("Error occurred while creating article tags: ", t)
                );
    }

    Result<AuthorDto> findProfile(UserId authorId) {
        return repository.findAuthorById(authorId)
                .map(Result::success)
                .orElseGet(() -> Result.failure(notFound("Author not exists")));
    }
}
