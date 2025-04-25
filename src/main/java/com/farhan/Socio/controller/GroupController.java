package com.farhan.Socio.controller;

import com.farhan.Socio.dto.GroupMemberUpdateDTO;
import com.farhan.Socio.dto.GroupRequestDTO;
import com.farhan.Socio.entity.Group;
import com.farhan.Socio.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequestDTO dto) {
        return ResponseEntity.ok(groupService.createGroup(dto));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/addUser")
    public ResponseEntity<Group> addUserToGroup(@RequestBody GroupMemberUpdateDTO dto) {
        return ResponseEntity.ok(groupService.addUserToGroup(dto));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/removeUser")
    public ResponseEntity<Group> removeUserFromGroup(@RequestBody GroupMemberUpdateDTO dto) {
        return ResponseEntity.ok(groupService.removeUserFromGroup(dto));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups(@RequestParam(value = "search", required = false) String search) {
        return ResponseEntity.ok(groupService.getAllGroups(search));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Group>> getUserGroups(@PathVariable Long userId) {
        return ResponseEntity.ok(groupService.getUserGroups(userId));
    }
}
