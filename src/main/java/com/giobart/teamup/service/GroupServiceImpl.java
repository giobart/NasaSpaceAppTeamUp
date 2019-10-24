package com.giobart.teamup.service;

import com.giobart.teamup.repository.GroupRepository;
import com.giobart.teamup.model.Group;
import com.giobart.teamup.model.User;
import com.giobart.teamup.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

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
    public void update(Group group, User u) {
        Group oldGroup = u.getGroup();

        if(group.getTelegramGroup()!=null){
            oldGroup.setTelegramGroup(group.getTelegramGroup());
        }

        if(group.getDescription()!=null){
            oldGroup.setDescription(group.getDescription());
        }

        if(group.getName()!=null){
            group.setName(group.getName().trim());
            if(groupRepository.findByName(group.getName())==null) {
                oldGroup.setName(group.getName());
            }
        }

        groupRepository.save(oldGroup);
    }

    @Override
    public void joinGroup(String group, String user) {
        User u = userService.findByUsername(user);
        if(u.getRoles().contains(roleRepository.getOne(3l))){
            return;
        }
        Group g = groupRepository.findByName(group);
        if(g.getGroupmates().size()<15 && u.getGroup()==null) {
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

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public List<Group> getAllAvailableGroups(User u) {
        return groupRepository
                .findAll()
                .stream()
                .filter(group -> group.getGroupmates().size()<16)
                .filter(group -> !group.getGroupFollowers().contains(u))
                .filter(group -> !group.getGroupmates().contains(u))
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> getAllFullGroups() {
        return groupRepository.findAll().stream().filter(group -> group.getGroupmates().size()==15).collect(Collectors.toList());
    }

    @Override
    public void followGroup(User u, String groupName) {
        Group group = groupRepository.findByName(groupName);

        //throw exception if group not exists or user is not a MENTOR
        if(group==null || u.getFollowingGroups().contains(roleRepository.getOne(3l))){
            throw new IllegalArgumentException();
        }

        //return if already following
        if(u.getFollowingGroups().contains(group)){
            return;
        }

        group.getGroupFollowers().add(u);
        u.getFollowingGroups().add(group);
        groupRepository.save(group);
    }

    @Override
    public void unfollowGroup(User u, String groupName) {

        Group group = groupRepository.findByName(groupName);

        //throw exception if group not exists or user is not a MENTOR
        if(group==null || u.getFollowingGroups().contains(roleRepository.getOne(3l))){
            throw new IllegalArgumentException();
        }

        group.getGroupFollowers().remove(u);
        u.getFollowingGroups().remove(group);
        groupRepository.save(group);

    }

}
