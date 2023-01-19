package com.modiconme.realworld.application.query;

import com.modiconme.realworld.application.ArticleMapper;
import com.modiconme.realworld.cqrs.QueryHandler;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.ArticleRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.query.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetArticleHandler implements QueryHandler<GetArticleResult, GetArticle> {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public GetArticleResult handle(GetArticle query) {
        // check slug exist
        String slug = query.getSlug();
        ArticleEntity article = articleRepository.findBySlugSearch(slug)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "article with slug [%s] is not found", slug));

        String currentUsername = query.getCurrentUsername();
        UserEntity user = userRepository.findByUsername(currentUsername)
                .orElse(null);

        return new GetArticleResult(ArticleMapper.mapToDto(article, user));
    }

}
