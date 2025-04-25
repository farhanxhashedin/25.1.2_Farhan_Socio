package com.farhan.Socio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class MethodSecurityConfig {
    // Empty class just for enabling method level security
}
