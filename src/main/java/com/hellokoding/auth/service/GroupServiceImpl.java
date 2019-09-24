package com.hellokoding.auth.service;

import com.hellokoding.auth.model.Group;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    @Override
    public void save(Group group, User u) {

        if(
                group.getName()!=null &&
                group.getDescription()!=null &&
                u.getGroup()==null &&
                groupRepository.findByName(group.getName())==null)
        {
            group.getGroupmates().add(u);
            u.setGroup(group);
            groupRepository.save(group);
        }
    }

    @Override
    public void joinGroup(String group, String user) {
        User u = userService.findByUsername(user);
        Group g = groupRepository.findByName(group);
        if(g.getGroupmates().size()<5 && u.getGroup()==null) {
            g.getGroupmates().add(u);
            u.setGroup(g);
            groupRepository.save(g);
        }
    }


    @Override
    public void removeFromGroup(String name) {
        User u = userService.findByUsername(name);
        Group g = u.getGroup();
        if(g!=null){
            g.getGroupmates().remove(u);
            u.setGroup(null);
            groupRepository.save(g);
            if(g.getGroupmates().isEmpty()){
                groupRepository.delete(g);
            }
        }
    }
}
