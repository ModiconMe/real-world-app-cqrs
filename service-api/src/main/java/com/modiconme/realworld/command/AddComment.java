package com.modiconme.realworld.command;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.modiconme.realworld.cqrs.Command;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@AllArgsConstructor
@Getter
@JsonRootName("comment")
public class AddComment implements Command<AddCommentResult> {

    @With
    private String authorUsername;
    @With
    private String slug;

    @NotEmpty
    private String body;

}
