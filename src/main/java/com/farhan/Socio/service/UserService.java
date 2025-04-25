package com.farhan.Socio.service;

import com.farhan.Socio.dto.LoginDTO;
import com.farhan.Socio.dto.SignInDTO;
import com.farhan.Socio.dto.UserUpdateRequestDTO;
import com.farhan.Socio.entity.User;
import com.farhan.Socio.entity.UserFollowerStatsProjection;
import com.farhan.Socio.repository.CommentRepository;
import com.farhan.Socio.repository.PostLikeRepository;
import com.farhan.Socio.repository.UserRelationRepository;
import com.farhan.Socio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserRelationRepository userRelationRepository;
    @Autowired
    private PasswordEncoder encoder;

    public UserService(UserRepository userRepository, UserRelationRepository userRelationRepository) {
        this.userRepository = userRepository;
        this.userRelationRepository = userRelationRepository;
    }


    public ResponseEntity<String> registerUser(SignInDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists.");
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword((encoder.encode(userDTO.getPassword())));
        user.setAdmin(userDTO.getEmail().endsWith("@socio.com"));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    public ResponseEntity<String> loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if (user != null && encoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.ok("Login successful.");
        }
        return ResponseEntity.status(401).body("Invalid credentials.");
    }

    public ResponseEntity<String> toggleProfileVisibility(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return ResponseEntity.badRequest().body("User not found.");
        user.setPrivate(!user.isPrivate());
        userRepository.save(user);
        return ResponseEntity.ok("Profile visibility updated.");
    }

    public ResponseEntity<String> makeAdmin(String email) {
        if (!email.endsWith("@socio.com")) {
            return ResponseEntity.badRequest().body("Only @socio.com emails can be admins.");
        }
        User user = userRepository.findByEmail(email);
        if (user == null) return ResponseEntity.badRequest().body("User not found.");
        user.setAdmin(true);
        userRepository.save(user);
        return ResponseEntity.ok("User promoted to admin.");
    }

    public ResponseEntity<String> changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }
        if (!encoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.status(403).body("Old password is incorrect.");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity.ok("Password updated successfully.");
    }

    public ResponseEntity<User> getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<String> deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        userRepository.delete(user);
        return ResponseEntity.ok("User deleted successfully.");
    }
    public ResponseEntity<String> updateUser(String id, UserUpdateRequestDTO dto) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userRepository.findById(id).get();

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getPassword() != null) user.setPassword(encoder.encode(dto.getPassword()));
        if (dto.getRole() != null) user.setRole(dto.getRole());
        if (dto.getIsPrivate() != null) user.setPrivate(dto.getIsPrivate());
        if (dto.getDob() != null) user.setDob(dto.getDob());

        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserFollowerStatsProjection> getUsersGroupedByDateOrderedByFollowers() {
        return userRelationRepository.getUsersGroupedByDateOrderedByFollowers();
    }
}
