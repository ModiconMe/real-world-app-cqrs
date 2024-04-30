package com.modiconme.realworld.it.base.builder;

import com.modiconme.realworld.domain.common.Password;
import com.modiconme.realworld.domain.common.PasswordEncoder;
import com.modiconme.realworld.domain.common.UserEntity;

import java.time.ZonedDateTime;

import static com.modiconme.realworld.it.base.TestDataGenerator.uniqEmail;
import static com.modiconme.realworld.it.base.TestDataGenerator.uniqString;

public class UserEntityTestBuilder implements TestBuilder<UserEntity> {

    private Long id;
    private String email = uniqEmail();
    private String password;
    private String username = uniqString();
    private String bio;
    private String image;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    private UserEntityTestBuilder() {
    }

    public static UserEntityTestBuilder aUser(PasswordEncoder passwordEncoder) {
        UserEntityTestBuilder builder = new UserEntityTestBuilder();
        builder.password("password", passwordEncoder);
        return builder;
    }

    @Override
    public UserEntity build() {
        return new UserEntity(id, email, password, username, bio, image, createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public UserEntityTestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntityTestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntityTestBuilder password(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(Password.emerge("password").getData());
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntityTestBuilder username(String username) {
        this.username = username;
        return this;
    }

    public String getBio() {
        return bio;
    }

    public UserEntityTestBuilder bio(String bio) {
        this.bio = bio;
        return this;
    }

    public String getImage() {
        return image;
    }

    public UserEntityTestBuilder image(String image) {
        this.image = image;
        return this;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public UserEntityTestBuilder createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UserEntityTestBuilder updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
