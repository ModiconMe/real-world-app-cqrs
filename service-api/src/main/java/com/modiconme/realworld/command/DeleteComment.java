package com.modiconme.realworld.command;

import com.modiconme.realworld.cqrs.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@AllArgsConstructor
@Getter
public class DeleteComment implements Command<DeleteCommentResult> {

    private String slug;
    private Long id;
    private String currentUsername;

}
