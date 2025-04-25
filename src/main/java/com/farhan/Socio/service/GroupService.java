package com.farhan.Socio.service;

import com.farhan.Socio.dto.GroupMemberUpdateDTO;
import com.farhan.Socio.dto.GroupRequestDTO;
import com.farhan.Socio.entity.Group;

import java.util.List;

public interface GroupService {
    Group createGroup(GroupRequestDTO dto);
    Group addUserToGroup(GroupMemberUpdateDTO dto);
    Group removeUserFromGroup(GroupMemberUpdateDTO dto);
    List<Group> getAllGroups(String search);
    List<Group> getUserGroups(Long userId);
}
