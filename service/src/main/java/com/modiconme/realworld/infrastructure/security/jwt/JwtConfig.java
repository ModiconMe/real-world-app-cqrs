package com.modiconme.realworld.infrastructure.security.jwt;

import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Get config from application.yml
 */
@Getter
@Component
public class JwtConfig {

    @Value("${jwt.sign-key}")
    private String singKey;

    @Value("${jwt.token-prefix}")
    private String tokenPrefix;

    @Value("${jwt.valid-time}")
    private Integer validTime;

    @Value("${jwt.issuer}")
    private String issuer;

    public Date getIssueAt() {
        return new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(validTime));
    }

    public Key getKey() {
        if (singKey.length() < 32)
            throw ApiException.exception(HttpStatus.UNPROCESSABLE_ENTITY, "signKey must have length at least 32");
        return Keys.hmacShaKeyFor(singKey.getBytes(StandardCharsets.UTF_8));
    }

}
