package com.modiconme.realworld.application.service;

import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.UserRepository;
import com.modiconme.realworld.infrastructure.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationContextHolderService {

    private final UserRepository userRepository;

    public String getCurrentUsername() {
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserDetails principal;
        String username = null;
        if (currentUser instanceof UserDetails) {
            principal = (AppUserDetails) currentUser;
            username = principal.getUsername(); // its email
        }
        Optional<UserEntity> optionalUser = userRepository.findByEmail(username);
        return optionalUser.map(UserEntity::getUsername).orElse(null);
    }

    public Long getUserId() {
        return ((AppUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUserId();
    }
}
