package com.modiconme.realworld.application.query;

import com.modiconme.realworld.application.CommentMapper;
import com.modiconme.realworld.cqrs.QueryHandler;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.ArticleRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.query.GetComments;
import com.modiconme.realworld.query.GetCommentsResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetCommentsHandler implements QueryHandler<GetCommentsResult, GetComments> {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public GetCommentsResult handle(GetComments query) {
        // find article
        String slug = query.getSlug();
        ArticleEntity article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "article with slug [%s] is not found", slug));

        // find current user if login
        UserEntity user = userRepository.findByUsername(query.getCurrentUsername()).orElse(null);

        return new GetCommentsResult(article.getComments().stream().map((c) -> CommentMapper.mapToDto(c, user)).toList());
    }

}
