package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.ArticleMapper;
import com.modiconme.realworld.application.service.SlugService;
import com.modiconme.realworld.command.UpdateArticle;
import com.modiconme.realworld.command.UpdateArticleResult;
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

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateArticleHandler implements CommandHandler<UpdateArticleResult, UpdateArticle> {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final SlugService slugService;

    @Override
    @Transactional
    public UpdateArticleResult handle(UpdateArticle cmd) {
        String oldSlug = cmd.getOldSlug();

        // check slug duplicate (if new title is provided)
        String newSlug = null;
        if (cmd.getTitle() != null) {
            newSlug = slugService.createSlug(cmd.getTitle());
            if (!newSlug.equals(oldSlug)) {
                Optional<ArticleEntity> bySlug = articleRepository.findBySlug(newSlug);
                if (bySlug.isPresent())
                    throw exception(HttpStatus.UNPROCESSABLE_ENTITY, "article with slug [%s] is already exist", newSlug);
            }
        }

        // find user that update article
        String currentUsername = cmd.getCurrentUsername();
        UserEntity user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with username [%s] is not found", currentUsername));

        // find article
        ArticleEntity article = articleRepository.findBySlug(oldSlug)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "article with slug [%s] is not found", oldSlug));

        // check that current user is owner of article
        UserEntity author = article.getAuthor();
        if (!Objects.equals(user, author))
            throw exception(HttpStatus.FORBIDDEN, "user with username [%s] is not owner of article [%s]", currentUsername, article.getSlug());

        // update article
        article = article.toBuilder()
                .slug(newSlug == null ? oldSlug : newSlug)
                .title(cmd.getTitle() == null ? article.getTitle() : cmd.getTitle())
                .body(cmd.getBody() == null ? article.getBody() : cmd.getBody())
                .description(cmd.getDescription() == null ? article.getDescription() : cmd.getDescription())
                .updatedAt(ZonedDateTime.now())
                .build();
        articleRepository.save(article);

        return new UpdateArticleResult(ArticleMapper.mapToDto(article, author));
    }

}
