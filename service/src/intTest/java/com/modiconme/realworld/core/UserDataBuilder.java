package com.modiconme.realworld.core;

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
        final var user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setBio(bio);
        user.setImage(image);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        return user;
    }
}
