package com.modiconme.realworld.infrastructure.security.jwt;

import com.modiconme.realworld.infrastructure.security.AuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final JwtConfig jwtConfig;
    private final AuthenticationProvider authenticationProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        checkToken(request);
        chain.doFilter(request, response);
    }

    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(token)) {
            log.info("Token is empty: [token='{}']", token);
            return;
        }

        if (!token.startsWith(jwtConfig.getTokenPrefix())) {
            log.info("Token has wrong prefix: [token='{}']", token);
            return;
        }

        String tokenBody = token.substring(jwtConfig.getTokenPrefix().length());

        if (!jwtUtils.isTokenValid(tokenBody)) {
            log.info("Token is invalid: [token='{}']", token);
            return;
        }

        log.info("Token is valid: [token='{}']", tokenBody);

        String username = jwtUtils.extractUsername(tokenBody);
        log.info("Extracted token username: [username='{}']", username);

        Authentication authentication = authenticationProvider.getAuthentication(username);
        log.info("Auth success: [authentication='{}']", authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)) // get auth header
//                .filter(authHeader -> authHeader.startsWith(jwtConfig.getTokenPrefix())) // search for bearer token
//                .map(authHeader -> authHeader.substring(jwtConfig.getTokenPrefix().length())) // remove token prefix
//                .filter(jwtUtils::isTokenValid) // check that token is not expired and username (email) is exists
//                .map(jwtUtils::extractUsername) // get username from token
//                .map(authenticationProvider::getAuthentication) // authenticate user -> get user from db by username -> set username and authorities to authentication
//                .ifPresent(SecurityContextHolder.getContext()::setAuthentication); // set authentication token to context
//        chain.doFilter(request, response);
//    }

}
