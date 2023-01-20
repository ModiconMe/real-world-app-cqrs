package com.modiconme.realworld.operation;

import com.modiconme.realworld.command.*;
import com.modiconme.realworld.query.*;
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

    @GetMapping("/articles/{slug}")
    GetArticleResult getArticle(@PathVariable("slug") String slug);


    @PostMapping("/articles")
    CreateArticleResult createArticle(@RequestBody CreateArticle command);

    @PutMapping("/articles/{slug}")
    UpdateArticleResult updateArticle(@PathVariable("slug") String slug, @RequestBody UpdateArticle command);

    @DeleteMapping("/articles/{slug}")
    void deleteArticle(@PathVariable("slug") String slug);

    @PostMapping("/articles/{slug}/comments")
    AddCommentResult addComment(@PathVariable("slug") String slug, @RequestBody AddComment command);

    @GetMapping("/articles/{slug}/comments")
    GetCommentsResult getComments(@PathVariable("slug") String slug);

    @DeleteMapping("/articles/{slug}/comments/{commentId}")
    void deleteComment(@PathVariable("slug") String slug, @PathVariable("commentId") String commentId);

    @PostMapping("/articles/{slug}/favorite")
    FavoriteArticleResult favoriteArticle(@PathVariable("slug") String slug);

    @DeleteMapping("/articles/{slug}/favorite")
    UnfavoriteArticleResult unfavoriteArticle(@PathVariable("slug") String slug);

}
