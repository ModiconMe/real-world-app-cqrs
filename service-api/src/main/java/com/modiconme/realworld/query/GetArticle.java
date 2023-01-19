package com.modiconme.realworld.query;

import com.modiconme.realworld.cqrs.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetArticle implements Query<GetArticleResult> {

    private String currentUsername;
    private String slug;

}
