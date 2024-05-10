package com.modiconme.realworld.command;

import com.modiconme.realworld.dto.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateArticleResult {

    private ArticleDto article;

}
