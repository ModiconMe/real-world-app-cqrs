package com.modiconme.realworld.command;

import com.modiconme.realworld.cqrs.Command;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DeleteArticle implements Command<DeleteArticleResult> { }
