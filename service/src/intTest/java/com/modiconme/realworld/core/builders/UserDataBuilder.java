package com.modiconme.realworld.core.builders;

import com.modiconme.realworld.core.TestDataBuilder;
import com.modiconme.realworld.domain.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor(staticName = "aUser")
@AllArgsConstructor
@With
public class UserDataBuilder implements TestDataBuilder<UserEntity> {

    private String username = UUID.randomUUID().toString();
    private String email = UUID.randomUUID() + "@mail.ru";
    private String password = "password";
    private String bio = "bio";
    private String image = "image";
    private ZonedDateTime createdAt = ZonedDateTime.now();
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    @Override
    public UserEntity build() {
        return UserEntity.builder()
                .username(username)
                .email(email)
                .password(password)
                .bio(bio)
                .image(image)
                .createdAt(createdAt)
                .updatedAt(updatedAt).build();
    }
}
