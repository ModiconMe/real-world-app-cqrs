package com.modiconme.realworld.application.query;

import com.modiconme.realworld.application.ArticleMapper;
import com.modiconme.realworld.application.service.SlugService;
import com.modiconme.realworld.cqrs.QueryHandler;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.TagEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.ArticleRepository;
import com.modiconme.realworld.domain.repository.TagRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.query.GetArticle;
import com.modiconme.realworld.query.GetArticleResult;
import com.modiconme.realworld.query.GetArticles;
import com.modiconme.realworld.query.GetArticlesResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetArticlesHandler implements QueryHandler<GetArticlesResult, GetArticles> {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public GetArticlesResult handle(GetArticles query) {
        List<ArticleEntity> articles = articleRepository.findByFilters(
                query.getTag(),
                query.getAuthor(),
                query.getFavoritedBy(),
                query.getOffset(),
                query.getLimit()
        );

        articles.forEach(a -> a.setTags(new TreeSet<>(a.getTags())));

        UserEntity user = userRepository.findByUsername(query.getCurrentUsername()).orElse(null);

        return new GetArticlesResult(articles.stream().map((a) -> ArticleMapper.mapToDto(a, user)).toList(), articles.size());
    }

}
