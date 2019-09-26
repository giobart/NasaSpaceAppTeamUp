package com.giobart.teamup.service;

import com.giobart.teamup.model.Role;
import com.giobart.teamup.model.User;
import com.giobart.teamup.repository.GroupRepository;
import com.giobart.teamup.model.Group;
import com.giobart.teamup.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;



    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        if(roleRepository.findAll().isEmpty()){
            Role role = new Role();
            role.setName("TEST");
            roleRepository.save(role);
            role = new Role();
            role.setName("TEST2");
            roleRepository.save(role);
        }
        if(userService.getAllUsers().isEmpty()){
            User u = new User();
            u.setUsername("giobarty");
            u.setSurname("aaa");
            u.setName("aaaa");
            u.setSkills("aaaaaaaa");
            u.setEmail("aaaaaaaaaaa");
            u.setPassword("12345678");
            u.setPasswordConfirm("12345678");
            userService.save(u);
        }

    }

}