package com.hellokoding.auth.service;

import com.hellokoding.auth.model.Group;
import com.hellokoding.auth.model.User;

public interface GroupService {
    void save(Group group,User u);

    void joinGroup(String groupname,String username);

    void removeFromGroup(String name);
}
