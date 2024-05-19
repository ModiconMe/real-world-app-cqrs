package com.modiconme.realworld.domain.articlecreate;

import java.util.List;

public record CreateArticleRequest(String title, String description, String body, List<String> tagList) {
}
