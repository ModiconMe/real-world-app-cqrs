package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.application.service.AuthenticationContextHolderService;
import com.modiconme.realworld.command.*;
import com.modiconme.realworld.cqrs.Bus;
import com.modiconme.realworld.operation.ArticleOperations;
import com.modiconme.realworld.query.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/")
public class ArticleController implements ArticleOperations {

    private final Bus bus;
    private final AuthenticationContextHolderService authService;

    @Override
    public GetArticlesResult listArticles(String tag, String author, String favoritedBy, String limit, String offset) {
        return bus.executeQuery(new GetArticles(
                tag, author, favoritedBy,
                limit, offset, authService.getCurrentUsername())
        );
    }

    @Override
    public GetFeedResult feedArticles(String limit, String offset) {
        return bus.executeQuery(new GetFeed(limit, offset, authService.getCurrentUsername()));
    }

    @Override
    public GetArticleResult getArticle(String slug) {
        return bus.executeQuery(new GetArticle(authService.getCurrentUsername(), slug));
    }

    @Override
    public CreateArticleResult createArticle(CreateArticle command) {
        return bus.executeCommand(command.withAuthorUsername(authService.getCurrentUsername()));
    }

    @Override
    public UpdateArticleResult updateArticle(String slug, UpdateArticle command) {
        return bus.executeCommand(command.withOldSlug(slug).withCurrentUsername(authService.getCurrentUsername()));
    }

    @Override
    public void deleteArticle(String slug) {
        bus.executeCommand(new DeleteArticle(authService.getCurrentUsername(), slug));
    }

    @Override
    public AddCommentResult addComment(String slug, AddComment command) {
        return bus.executeCommand(command.withSlug(slug).withAuthorUsername(authService.getCurrentUsername()));
    }

    @Override
    public GetCommentsResult getComments(String slug) {
        return bus.executeQuery(new GetComments(authService.getCurrentUsername(), slug));
    }

    @Override
    public void deleteComment(String slug, String commentId) {
        bus.executeCommand(new DeleteComment(slug, Long.valueOf(commentId), authService.getCurrentUsername()));
    }
}
