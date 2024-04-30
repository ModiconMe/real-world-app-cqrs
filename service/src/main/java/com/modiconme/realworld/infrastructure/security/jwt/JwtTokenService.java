package com.modiconme.realworld.infrastructure.security.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {

    private final JwtConfig jwtConfig;

    @Override
    public String getAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer(jwtConfig.getIssuer())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(jwtConfig.getValidTime(), ChronoUnit.HOURS)))
                .signWith(jwtConfig.getKey()).compact();
    }

}