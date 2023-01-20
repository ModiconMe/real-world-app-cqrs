package com.modiconme.realworld.query;

import com.modiconme.realworld.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetCommentsResult {

    private List<CommentDto> comments;

}
