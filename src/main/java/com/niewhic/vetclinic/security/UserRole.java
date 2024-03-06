package com.niewhic.vetclinic.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static com.niewhic.vetclinic.security.UserPermission.*;

public enum UserRole {
    ADMIN,
    USER;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(this.name().equals("USER")){
            authorities.add(new SimpleGrantedAuthority(READ.name()));
        }
        if(this.name().equals("ADMIN")){
            authorities.add(new SimpleGrantedAuthority(WRITE.name()));
            authorities.add(new SimpleGrantedAuthority(READ.name()));
            authorities.add(new SimpleGrantedAuthority(DELETE.name()));
        }
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}
