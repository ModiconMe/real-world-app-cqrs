package com.modiconme.realworld.domain.userupdate;

public record UpdateUserRequest(String email, String username, String password, String bio, String image) {
}
