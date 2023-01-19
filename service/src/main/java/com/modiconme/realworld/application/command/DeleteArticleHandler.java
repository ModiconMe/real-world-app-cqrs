package com.modiconme.realworld.application.command;

import com.modiconme.realworld.command.DeleteArticle;
import com.modiconme.realworld.command.DeleteArticleResult;
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

import java.util.Objects;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeleteArticleHandler implements CommandHandler<DeleteArticleResult, DeleteArticle> {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public DeleteArticleResult handle(DeleteArticle cmd) {
        // find article
        String slug = cmd.getSlug();
        ArticleEntity article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "article with slug [%s] is not found", slug));

        // find user that update article
        String currentUsername = cmd.getCurrentUsername();
        UserEntity user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with username [%s] is not found", currentUsername));

        // check that current user is owner of article
        UserEntity author = article.getAuthor();
        if (!Objects.equals(user.getId(), author.getId()))
            throw exception(HttpStatus.FORBIDDEN, "user with username [%s] is not owner of article [%s]", currentUsername, article.getSlug());

        articleRepository.delete(article);

        return new DeleteArticleResult();
    }

}
