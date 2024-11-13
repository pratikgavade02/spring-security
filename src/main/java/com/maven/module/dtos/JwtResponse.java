package com.maven.module.dtos;

import lombok.Data;

@Data
public class JwtResponse {
    private String username;
    private String token;
    private String role;
}
