package com.giobart.teamup.service;

import com.giobart.teamup.model.Group;
import com.giobart.teamup.model.User;

public interface GroupService {
    void save(Group group,User u);

    void joinGroup(String groupname,String username);

    void removeFromGroup(String name);
}
