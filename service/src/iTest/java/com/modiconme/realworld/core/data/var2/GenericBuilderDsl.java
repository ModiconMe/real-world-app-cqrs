package com.modiconme.realworld.core.data.var2;

import com.modiconme.realworld.command.UpdateUser;
import com.modiconme.realworld.core.data.ArticleBuilder;
import com.modiconme.realworld.core.data.UpdateUserBuilder;
import com.modiconme.realworld.core.data.UserBuilder;
import com.modiconme.realworld.core.data.var1.TestDbFacade;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.UserEntity;

public abstract class GenericBuilderDsl {

    public static <T, B extends GenericBuilder<T, B>> T given(GenericBuilder<T, B> builder) {
        return builder.build();
    }

    public static <T, B extends GenericBuilder<T, B>> T given(GenericBuilder<T, B> builder, TestDbFacade db) {
        return db.persisted(builder.build());
    }

    public static UserBuilder<UserEntity> user() {
        return new UserBuilder<>();
    }

    public static UpdateUserBuilder<UpdateUser> updateUser(UserEntity user) {
        return new UpdateUserBuilder<>(user);
    }

    public static ArticleBuilder<ArticleEntity> article(UserEntity user) {
        return new ArticleBuilder<>(user);
    }
}
