package com.farhan.Socio.security;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
