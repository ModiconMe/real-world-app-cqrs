package com.modiconme.realworld.application.query;

import com.modiconme.realworld.application.ArticleMapper;
import com.modiconme.realworld.cqrs.QueryHandler;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.ArticleRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.query.GetFeed;
import com.modiconme.realworld.query.GetFeedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetFeedHandler implements QueryHandler<GetFeedResult, GetFeed> {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public GetFeedResult handle(GetFeed query) {
        UserEntity user = userRepository.findByUsername(query.getCurrentUsername()).orElse(null);
        List<ArticleEntity> articles = articleRepository.findByFeed(
                query.getCurrentUsername(),
                query.getOffset(),
                query.getLimit()
        );

        return new GetFeedResult(articles.stream().map(a -> ArticleMapper.mapToDto(a, user)).toList(), articles.size());
    }

}
