package com.giobart.teamup.service;

import com.giobart.teamup.model.Group;
import com.giobart.teamup.model.User;

import java.util.List;

public interface GroupService {
    void save(Group group,User u);

    void joinGroup(String groupname,String username);

    void removeFromGroup(String name);

    List<Group> getAllGroups();

    List<Group> getAllAvailableGroups();

    List<Group> getAllFullGroups();

}
