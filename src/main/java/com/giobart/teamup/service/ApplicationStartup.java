package com.giobart.teamup.service;

import com.giobart.teamup.model.Role;
import com.giobart.teamup.repository.GroupRepository;
import com.giobart.teamup.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
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
            role.setName("ADMIN");
            roleRepository.save(role);
            role = new Role();
            role.setName("USER");
            roleRepository.save(role);
            role=new Role();
            role.setName("MENTOR");
            roleRepository.save(role);
        }

    }

}