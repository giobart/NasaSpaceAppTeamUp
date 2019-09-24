package com.giobart.teamup.service;

import com.giobart.teamup.repository.GroupRepository;
import com.giobart.teamup.model.Group;
import com.giobart.teamup.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
