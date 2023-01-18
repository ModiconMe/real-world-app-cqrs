package com.modiconme.realworld.query;

import com.modiconme.realworld.cqrs.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetProfile implements Query<GetProfileResult> {

    private String userUsername;
    private String profileUsername;

}
