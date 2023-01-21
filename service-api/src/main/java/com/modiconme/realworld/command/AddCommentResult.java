package com.modiconme.realworld.command;

import com.modiconme.realworld.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentResult {

    private CommentDto comment;

}
