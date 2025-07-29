package com.mg.identity_service.model;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_AUTHOR,
    ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
