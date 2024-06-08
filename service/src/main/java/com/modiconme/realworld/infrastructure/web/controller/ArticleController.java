package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.domain.articlecreate.CreateArticleRequest;
import com.modiconme.realworld.domain.articlecreate.CreateArticleResponse;
import com.modiconme.realworld.domain.articlecreate.CreateArticleService;
import com.modiconme.realworld.domain.articleupdate.UpdateArticleRequest;
import com.modiconme.realworld.domain.articleupdate.UpdateArticleResponse;
import com.modiconme.realworld.domain.articleupdate.UpdateArticleService;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class ArticleController {

    private final CreateArticleService createArticleService;
    private final UpdateArticleService updateArticleService;

    @PostMapping("/articles")
    public ResponseEntity<RestResponse<CreateArticleResponse>> createArticle(
            @RequestBody CreateArticleRequest command, @AuthenticationPrincipal AppUserDetails user
    ) {
        return createArticleService.createArticle(command, user.getUserId())
                .toRestResponse();
    }

    @PutMapping("/articles/{slug}")
    public ResponseEntity<RestResponse<UpdateArticleResponse>> updateArticle(
            @PathVariable String slug,
            @RequestBody UpdateArticleRequest command,
            @AuthenticationPrincipal AppUserDetails user
    ) {
        return updateArticleService.updateArticle(command, slug, user.getUserId())
                .toRestResponse();
    }

}