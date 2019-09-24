package com.hellokoding.auth.service;

import com.hellokoding.auth.model.Group;
import com.hellokoding.auth.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    GroupRepository groupRepository;


    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        Group group = new Group();
        group.setName("The best programmers of the world");
        group.setDescription("we are awesome");
        groupRepository.save(group);

    }

}