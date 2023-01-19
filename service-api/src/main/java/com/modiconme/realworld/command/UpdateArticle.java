package com.modiconme.realworld.command;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.modiconme.realworld.cqrs.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@AllArgsConstructor
@Getter
@JsonRootName("article")
public class UpdateArticle implements Command<UpdateArticleResult> {

    @With
    private String oldSlug;
    @With
    private String currentUsername;

    private String title;
    private String description;
    private String body;

}
