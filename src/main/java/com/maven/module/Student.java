package com.maven.module;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity

public class Student implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;
    private String username;
    private Boolean enable;
    private String password;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] img;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<Authority> authorities = new HashSet<>();
        Authority auth =new Authority();
        auth.setAuthority(role);
        authorities.add(auth);
        return authorities;

    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
