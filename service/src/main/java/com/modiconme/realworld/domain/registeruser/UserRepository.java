package com.modiconme.realworld.domain.registeruser;

import com.modiconme.realworld.domain.common.Email;
import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.domain.common.UserEntity;

public interface UserRepository {

    Result<Boolean> existByEmail(String email);
    Result<UserEntity> save(UserEntity user);
    Result<UserEntity> findByEmail(String email);
}
