package com.example.assesment.model._enum;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;

    public String getAuthority() {
        return name();
    }

    public static Role getRoleFromString(String str) {
        switch (str) {
            case "ROLE_USER": return Role.ROLE_USER;
            case "ROLE_ADMIN": return Role.ROLE_ADMIN;
            default: throw new IllegalArgumentException("Role [" + str + "] not supported.");
        }
    }

}