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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddCommentHandler implements CommandHandler<AddCommentResult, AddComment> {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AddCommentResult handle(AddComment cmd) {
        ArticleEntity article = ArticleEntity.getExistedArticleOrThrow(cmd.getSlug(), articleRepository);
        UserEntity author = UserEntity.getExistedUserOrThrow(cmd.getAuthorUsername(), userRepository);
        CommentEntity comment = author.writeComment(cmd.getBody(), article, articleRepository);
        log.info("add comment {} to article {}", comment, article);
        return new AddCommentResult(CommentMapper.mapToDto(comment, author));
    }

}
