package com.modiconme.realworld.infrastructure.security;

import com.modiconme.realworld.domain.common.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

@RequiredArgsConstructor
public class AppUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = -7745847682848118234L;

    @Getter
    private final long userId;
    private final String email;
    private final String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static AppUserDetails fromUser(UserEntity user) {
        return new AppUserDetails(user.getId(), user.getEmail(), user.getPassword());
    }

}
