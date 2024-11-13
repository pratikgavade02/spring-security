package com.maven.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {

    private String authority;
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
