package com.farhan.Socio.controller;

import com.farhan.Socio.dto.LoginDTO;
import com.farhan.Socio.dto.SignInDTO;
import com.farhan.Socio.dto.UserUpdateRequestDTO;
import com.farhan.Socio.entity.User;
import com.farhan.Socio.entity.UserFollowerStatsProjection;
import com.farhan.Socio.service.UserService;
import com.farhan.Socio.service.UserUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final UserUploadService userUploadService;

    public UserController(UserService userService, UserUploadService userUploadService) {
        this.userService = userService;
        this.userUploadService = userUploadService;
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody SignInDTO userDTO) {
//        return userService.registerUser(userDTO);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
//        return userService.loginUser(loginDTO);
//    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/profile/visibility")
    public ResponseEntity<String> toggleVisibility(@RequestParam String email) {
        return userService.toggleProfileVisibility(email);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/make-admin")
    public ResponseEntity<String> makeAdmin(@RequestParam String email) {
        return userService.makeAdmin(email);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserUpdateRequestDTO dto) {
        return userService.updateUser(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER'')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/followers/grouped")
    public ResponseEntity<List<UserFollowerStatsProjection>> getUsersGroupedByFollowerDate() {
        return ResponseEntity.ok(userService.getUsersGroupedByDateOrderedByFollowers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadUsers(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(userUploadService.uploadUsers(file));
    }

}
