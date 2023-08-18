package com.modiconme.realworld.application.query;

import com.modiconme.realworld.application.ArticleMapper;
import com.modiconme.realworld.cqrs.QueryHandler;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.ArticleRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.query.GetArticles;
import com.modiconme.realworld.query.GetArticlesResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.TreeSet;

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

        return new GetArticlesResult(articles.stream().map(a -> ArticleMapper.mapToDto(a, user)).toList(), articles.size());
    }

}
