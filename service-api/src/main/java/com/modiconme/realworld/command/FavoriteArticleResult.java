package com.modiconme.realworld.command;

import com.modiconme.realworld.dto.ArticleDto;
import com.modiconme.realworld.dto.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FavoriteArticleResult {

    private ArticleDto articleDto;

}
