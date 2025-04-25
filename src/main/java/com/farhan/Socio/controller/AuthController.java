package com.farhan.Socio.controller;


import com.farhan.Socio.dto.SignInDTO;
import com.farhan.Socio.entity.Role;
import com.farhan.Socio.entity.User;
import com.farhan.Socio.repository.UserRepository;
import com.farhan.Socio.security.AuthRequest;
import com.farhan.Socio.security.AuthResponse;
import com.farhan.Socio.security.CustomUserDetailsService;
import com.farhan.Socio.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Authentication authenticate = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignInDTO signInDTO) {
        if (userRepository.findByEmail(signInDTO.getEmail()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body("Email is already in use");
        }

        User newUser = new User()
                .setId(UUID.randomUUID().toString())
                .setName(signInDTO.getName())
                .setEmail(signInDTO.getEmail())
                .setPassword(passwordEncoder.encode(signInDTO.getPassword()))
                .setRole(signInDTO.getEmail().endsWith("@socio.com") ? Role.ADMIN : Role.USER)
                .setAdmin(signInDTO.getEmail().endsWith("@socio.com"));

        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully");
    }
}
