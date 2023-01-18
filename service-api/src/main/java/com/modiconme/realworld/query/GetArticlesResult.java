package com.modiconme.realworld.query;

import com.modiconme.realworld.dto.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetArticlesResult {

    private List<ArticleDto> articles;

}
