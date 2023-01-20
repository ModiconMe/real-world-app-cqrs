package com.modiconme.realworld.application;

import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.TagEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.ArticleDto;
import com.modiconme.realworld.dto.ProfileDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class ArticleMapper {

    public static ArticleDto mapToDto(ArticleEntity article, UserEntity currentUser) {
        Set<UserEntity> favoriteList = article.getFavoriteList();
        boolean isFavorited = favoriteList.stream().anyMatch((f) -> f.equals(currentUser));
        long size = favoriteList.size();
        ProfileDto author = ProfileMapper.mapToDto(article.getAuthor(), currentUser);
        List<String> tagList = article.getTags().stream().map(TagEntity::getTagName).toList();

        return ArticleDto.builder()
                .slug(article.getSlug())
                .title(article.getTitle())
                .body(article.getBody())
                .description(article.getDescription())
                .author(author)
                .tagList(tagList)
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .favorited(isFavorited)
                .favoritesCount(size)
                .build();
    }

}
