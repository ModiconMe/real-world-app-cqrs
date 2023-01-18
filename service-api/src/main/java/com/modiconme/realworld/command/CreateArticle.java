package com.modiconme.realworld.command;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.modiconme.realworld.cqrs.Command;
import jakarta.validation.constraints.Email;
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
public class CreateArticle implements Command<CreateArticleResult> {

    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotEmpty
    private String body;

    private List<String> tags;

}
