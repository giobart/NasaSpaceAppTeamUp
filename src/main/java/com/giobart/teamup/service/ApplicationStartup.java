package com.giobart.teamup.service;

import com.giobart.teamup.model.User;
import com.giobart.teamup.repository.GroupRepository;
import com.giobart.teamup.model.Group;
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


    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {


    }

}