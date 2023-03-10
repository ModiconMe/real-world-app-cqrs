package com.modiconme.realworld.command;

import com.modiconme.realworld.cqrs.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FavoriteArticle implements Command<FavoriteArticleResult> {

    private String currentUsername;
    private String slug;

}
