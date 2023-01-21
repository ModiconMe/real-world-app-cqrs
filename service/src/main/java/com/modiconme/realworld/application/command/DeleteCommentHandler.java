package com.modiconme.realworld.application.command;

import com.modiconme.realworld.command.DeleteComment;
import com.modiconme.realworld.command.DeleteCommentResult;
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

import java.util.Objects;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeleteCommentHandler implements CommandHandler<DeleteCommentResult, DeleteComment> {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public DeleteCommentResult handle(DeleteComment cmd) {
        // find author
        String currentUsername = cmd.getCurrentUsername();
        UserEntity user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with username [%s] is not found", currentUsername));

        // find article
        String slug = cmd.getSlug();
        ArticleEntity article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "article with slug [%s] is not found", slug));

        // find comment
        Long commentId = cmd.getId();
        CommentEntity comment = article.getComments().stream().filter((c) -> c.getId().equals(commentId)).findFirst()
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "comment with id [%d] is not found", commentId));

        // check that current user is owner of comment
        UserEntity author = comment.getAuthor();
        if (!Objects.equals(user, author))
            throw exception(HttpStatus.FORBIDDEN, "user with username [%s] is not owner of comment with id [%d]", currentUsername, commentId);

        article.getComments().remove(comment);
        articleRepository.save(article);
        log.info("delete comment {} of article {}", comment, article);

        return new DeleteCommentResult();
    }

}
