package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.ArticleMapper;
import com.modiconme.realworld.command.FavoriteArticle;
import com.modiconme.realworld.command.FavoriteArticleResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.ArticleRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class FavoriteArticleHandler implements CommandHandler<FavoriteArticleResult, FavoriteArticle> {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public FavoriteArticleResult handle(FavoriteArticle cmd) {
        // find author
        String currentUsername = cmd.getCurrentUsername();
        UserEntity user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with username [%s] is not found", currentUsername));

        // find article
        String slug = cmd.getSlug();
        ArticleEntity article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "article with slug [%s] is not found", slug));

        article = article.toBuilder()
                .favoriteList(user)
                .build();
        articleRepository.save(article);

        return new FavoriteArticleResult(ArticleMapper.mapToDto(article, user));
    }

}
