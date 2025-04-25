package com.farhan.Socio.service;

import com.farhan.Socio.dto.GroupMemberUpdateDTO;
import com.farhan.Socio.dto.GroupRequestDTO;
import com.farhan.Socio.entity.Group;
import com.farhan.Socio.entity.User;
import com.farhan.Socio.repository.GroupRepository;
import com.farhan.Socio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public Group createGroup(GroupRequestDTO dto) {
        User creator = userRepository.findById(String.valueOf(dto.getCreatedByUserId())).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = new Group();
        group.setName(dto.getName());
        group.setCreatedBy(creator);
        group.getMembers().add(creator); // Add creator as member
        return groupRepository.save(group);
    }

    @Override
    public Group addUserToGroup(GroupMemberUpdateDTO dto) {
        Group group = groupRepository.findById(dto.getGroupId()).orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findById(String.valueOf(dto.getUserId())).orElseThrow(() -> new RuntimeException("User not found"));
        group.getMembers().add(user);
        return groupRepository.save(group);
    }

    @Override
    public Group removeUserFromGroup(GroupMemberUpdateDTO dto) {
        Group group = groupRepository.findById(dto.getGroupId()).orElseThrow(() -> new RuntimeException("Group not found"));
        User user = userRepository.findById(String.valueOf(dto.getUserId())).orElseThrow(() -> new RuntimeException("User not found"));
        group.getMembers().remove(user);
        return groupRepository.save(group);
    }

    @Override
    public List<Group> getAllGroups(String search) {
        if (search != null && !search.isEmpty()) {
            return groupRepository.findByNameContainingIgnoreCase(search);
        }
        return groupRepository.findAll();
    }

    @Override
    public List<Group> getUserGroups(Long userId) {
        User user = userRepository.findById(String.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not found"));
        return groupRepository.findAllByUserInvolved(userId, user);
    }
}
