package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.CommentMapper;
import com.modiconme.realworld.command.AddComment;
import com.modiconme.realworld.command.AddCommentResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.CommentEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.ArticleRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddCommentHandler implements CommandHandler<AddCommentResult, AddComment> {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AddCommentResult handle(AddComment cmd) {
        // find author
        String authorUsername = cmd.getAuthorUsername();
        UserEntity user = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with username [%s] is not found", authorUsername));

        // find article
        String slug = cmd.getSlug();
        ArticleEntity article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "article with slug [%s] is not found", slug));

        CommentEntity comment = CommentEntity.builder()
                .body(cmd.getBody())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .author(user)
                .build();
        article = article.toBuilder()
                .comment(comment)
                .build();
        articleRepository.save(article);
        log.info("add comment {} to article {}", comment, article);

        return new AddCommentResult(CommentMapper.mapToDto(comment, user));
    }

}
