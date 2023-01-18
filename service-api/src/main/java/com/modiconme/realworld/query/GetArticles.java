package com.modiconme.realworld.query;

import com.modiconme.realworld.cqrs.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetArticles implements Query<GetArticlesResult> {

    private String tag;
    private String author;
    private String favoritedBy;
    private String limit;
    private String offset;

}
