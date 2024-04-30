package com.modiconme.realworld.infrastructure.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    String getAccessToken(UserDetails userDetails);
}
