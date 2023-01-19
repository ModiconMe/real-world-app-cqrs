package com.modiconme.realworld.query;

import com.modiconme.realworld.cqrs.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetFeed implements Query<GetFeedResult> {

    private String limit;
    private String offset;
    private String currentUsername;

}
