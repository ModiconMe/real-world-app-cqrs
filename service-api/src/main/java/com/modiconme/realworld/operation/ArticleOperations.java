package com.modiconme.realworld.operation;

import com.modiconme.realworld.command.CreateArticle;
import com.modiconme.realworld.command.CreateArticleResult;
import com.modiconme.realworld.command.DeleteArticleResult;
import com.modiconme.realworld.command.UpdateArticleResult;
import com.modiconme.realworld.query.GetArticlesResult;
import com.modiconme.realworld.query.GetFeedResult;
import org.springframework.web.bind.annotation.*;

public interface ArticleOperations {

    @GetMapping("/articles")
    GetArticlesResult listArticles(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "favorited", required = false) String favoritedBy,
            @RequestParam(value = "limit", defaultValue = "20") String limit,
            @RequestParam(value = "offset", defaultValue = "0") String offset
    );

    @GetMapping("/articles/feed")
    GetFeedResult feedArticles(
            @RequestParam(value = "limit", defaultValue = "20") String limit,
            @RequestParam(value = "offset", defaultValue = "0") String offset
    );

    @PutMapping("/articles")
    CreateArticleResult createArticle(CreateArticle command);

    @PostMapping("/articles/{slug}")
    UpdateArticleResult updateArticle(@PathVariable("slug") String slug);

    @DeleteMapping("/articles/{slug}")
    DeleteArticleResult deleteArticle(@PathVariable("slug") String slug);

}
