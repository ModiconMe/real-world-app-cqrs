package com.modiconme.realworld.application.service;

import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import com.modiconme.realworld.infrastructure.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@RequiredArgsConstructor
@Service
public class AuthenticationContextHolderService {

    private final UserRepository userRepository;

    public String getCurrentUsername() {
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserDetails principal = (AppUserDetails) currentUser;
        Optional<UserEntity> optionalUser = userRepository.findByEmail(principal.getUsername());
        return optionalUser.map(UserEntity::getUsername).orElse(null);
    }
}
