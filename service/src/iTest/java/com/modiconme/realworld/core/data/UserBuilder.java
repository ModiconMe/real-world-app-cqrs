package com.modiconme.realworld.core.data;

import com.modiconme.realworld.core.data.var2.GenericBuilder;
import com.modiconme.realworld.domain.model.UserEntity;

import java.time.ZonedDateTime;

import static com.modiconme.realworld.core.data.var2.GenericBuilder.uniqEmail;
import static com.modiconme.realworld.core.data.var2.GenericBuilder.uniqString;

public class UserBuilder<T extends UserEntity> implements GenericBuilder<T, UserBuilder<T>> {

    private String username = uniqString();
    private String email = uniqEmail();
    private String password = uniqString();
    private String bio = uniqString();
    private String image = uniqString();
    private ZonedDateTime createdAt = ZonedDateTime.now();
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    public UserBuilder<T> username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder<T> email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder<T> password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder<T> bio(String bio) {
        this.bio = bio;
        return this;
    }

    public UserBuilder<T> image(String image) {
        this.image = image;
        return this;
    }

    public UserBuilder<T> createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserBuilder<T> updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public T build() {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setBio(bio);
        user.setImage(image);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        return (T) user;
    }
}
