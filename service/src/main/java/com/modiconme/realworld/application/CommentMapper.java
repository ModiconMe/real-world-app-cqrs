package com.modiconme.realworld.application;

import com.modiconme.realworld.domain.model.CommentEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.dto.CommentDto;
import com.modiconme.realworld.dto.ProfileDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentMapper {

    public static CommentDto mapToDto(CommentEntity comment, UserEntity currentUser) {
        ProfileDto author = ProfileMapper.mapToDto(comment.getAuthor(), currentUser);

        return CommentDto.builder()
                .id(comment.getId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .body(comment.getBody())
                .author(author)
                .build();
    }

}
