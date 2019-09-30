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

//        if(roleRepository.findAll().isEmpty()){
//            Role role = new Role();
//            role.setName("ADMIN");
//            roleRepository.save(role);
//            role = new Role();
//            role.setName("USER");
//            roleRepository.save(role);
//            role=new Role();
//            role.setName("MENTOR");
//            roleRepository.save(role);
//        }
//        if(userService.getAllUsers().isEmpty()){
//            User u = new User();
//            u.setUsername("giobarty");
//            u.setSurname("aaa");
//            u.setName("aaaa");
//            u.setSkills("aaaaaaaa");
//            u.setEmail("aaaaaaaaaaa");
//            u.setDegreeCourse("IT");
//            u.setPassword("12345678");
//            u.setPasswordConfirm("12345678");
//            u.getRoles().add(roleRepository.getOne(1l));
//            userService.save(u);
//
//            u = new User();
//            u.setUsername("giobarty2");
//            u.setSurname("aaa");
//            u.setName("aaaa");
//            u.setSkills("aaaaaaaa");
//            u.setEmail("aaaaaaaaaaa");
//            u.setDegreeCourse("Economy");
//            u.setPassword("12345678");
//            u.setPasswordConfirm("12345678");
//            u.getRoles().add(roleRepository.getOne(3l));
//            userService.save(u);
       // }

    }

}