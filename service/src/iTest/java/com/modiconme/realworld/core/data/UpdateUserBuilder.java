package com.modiconme.realworld.core.data;

import com.modiconme.realworld.command.UpdateUser;
import com.modiconme.realworld.core.data.var2.GenericBuilder;
import com.modiconme.realworld.domain.model.UserEntity;

public class UpdateUserBuilder<T extends UpdateUser> implements GenericBuilder<T, UpdateUserBuilder<T>> {

    private long id;
    private String username;
    private String email;
    private String password;
    private String bio;
    private String image;

    public UpdateUserBuilder(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.bio = user.getBio();
        this.image = user.getImage();
    }

    public UpdateUserBuilder<T> withNewUsername(String username) {
        this.username = username;
        return this;
    }

    public UpdateUserBuilder<T> withNewEmail(String email) {
        this.email = email;
        return this;
    }

    public UpdateUserBuilder<T> withNewPassword(String password) {
        this.password = password;
        return this;
    }

    public UpdateUserBuilder<T> withNewBio(String bio) {
        this.bio = bio;
        return this;
    }

    public UpdateUserBuilder<T> withNewImage(String image) {
        this.image = image;
        return this;
    }

    @Override
    public T build() {
        return (T) new UpdateUser(id, email, username, password, bio, image);
    }
}
