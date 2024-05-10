package com.modiconme.realworld.infrastructure.security.jwt;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.infrastructure.security.AuthenticationProvider;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
import java.time.Instant;
import java.util.Date;

// TODO exclude filter from unauthorized endpoints
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final AuthenticationProvider authenticationProvider;

    private static Result<String> getHeader(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header)) {
            return Result.success(header);
        }
        log.warn("Token is empty: [header='{}']", header);
        return Result.failure(ApiException.unauthorized("Authorization header not found"));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        checkToken(request);
        chain.doFilter(request, response);
    }

    public void checkToken(HttpServletRequest request) {
        Result<Authentication> authResult = getHeader(request)
                .ensure(this::isTokenStartsWithPrefix,
                        Result.failure(ApiException.unauthorized("Token has wrong prefix")))
                .map(this::extractToken)
                .map(this::extractClaims)
                .ensure(this::isTokenNotExpired,
                        Result.failure(ApiException.unauthorized("Token is expired: [token='{}']")))
                .map(Claims::getSubject)
                .flatMap(authenticationProvider::getAuthentication)
                .onSuccess(it -> SecurityContextHolder.getContext().setAuthentication(it));

        if (authResult.isSuccess()) {
            log.info("Auth success: [authentication='{}']", authResult.getData());
            return;
        }

        log.info("Auth failure:", authResult.getError());
    }

    private boolean isTokenStartsWithPrefix(String it) {
        return it.startsWith(jwtConfig.getTokenPrefix());
    }

    private String extractToken(String it) {
        return it.substring(jwtConfig.getTokenPrefix().length());
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenNotExpired(Claims claims) {
        Instant now = Instant.now();
        Date exp = claims.getExpiration();
        return !exp.before(Date.from(now));
    }
}
