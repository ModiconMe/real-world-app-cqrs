package com.modiconme.realworld.query;

import com.modiconme.realworld.dto.ArticleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetFeedResult {

    private List<ArticleDto> articles;
    private Integer articlesCount;

}
