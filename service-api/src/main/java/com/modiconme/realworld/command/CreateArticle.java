package com.modiconme.realworld.command;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.modiconme.realworld.cqrs.Command;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.util.List;

@AllArgsConstructor
@Getter
@JsonRootName("article")
public class CreateArticle implements Command<CreateArticleResult> {

    @With
    private String authorUsername;

    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotEmpty
    private String body;

    private List<String> tagList;

}
