package com.giobart.teamup.service;

import com.giobart.teamup.model.Group;
import com.giobart.teamup.model.User;

import java.util.List;

public interface GroupService {
    void save(Group group,User u);

    void update(Group group,User u);

    void joinGroup(String groupname,String username);

    void removeFromGroup(String name);

    List<Group> getAllGroups();

    List<Group> getAllAvailableGroups(User u);

    List<Group> getAllFullGroups();

    void followGroup(User u, String groupName);

    void unfollowGroup(User u, String groupName);

}
