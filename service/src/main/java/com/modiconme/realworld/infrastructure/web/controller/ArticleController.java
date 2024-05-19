package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.articlecreate.CreateArticleRequest;
import com.modiconme.realworld.domain.articlecreate.CreateArticleResponse;
import com.modiconme.realworld.domain.articlecreate.CreateArticleService;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class ArticleController {

    private final CreateArticleService createArticleService;

    @PostMapping("/articles")
    public ResponseEntity<RestResponse<CreateArticleResponse>> createArticle(
            @RequestBody CreateArticleRequest command, @AuthenticationPrincipal AppUserDetails user
    ) {
        return createArticleService.createArticle(command, user.getUserId()).toRestResponse();
    }

}