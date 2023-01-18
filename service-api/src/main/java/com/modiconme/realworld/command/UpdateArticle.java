package com.modiconme.realworld.command;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.modiconme.realworld.cqrs.Command;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonRootName("article")
public class UpdateArticle implements Command<UpdateArticleResult> {

    private String title;
    private String description;
    private String body;

}
