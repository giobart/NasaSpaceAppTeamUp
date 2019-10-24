package com.giobart.teamup.validator;

import com.giobart.teamup.model.Group;
import com.giobart.teamup.model.User;
import com.giobart.teamup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GroupInfoValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Group.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Group group = (Group) o;

        //name check
        if(group.getName().isEmpty() || group.getName() == null){
            group.setName(null);
        }else
        if (group.getName().length()>30 || group.getName().length()<4 ){
            errors.rejectValue("name", "Groupname.valid");
        }

        //telegram link check
        if(group.getTelegramGroup().isEmpty() || group.getTelegramGroup() == null){
          group.setTelegramGroup(null);
        }else
        if (group.getTelegramGroup().length()>100){
            errors.rejectValue("telegramGroup", "GroupLink.valid");
        }

        //description check
        if(group.getDescription().isEmpty() || group.getDescription()==null ){
            group.setDescription(null);
        }else
        if (group.getDescription().length()>50 || group.getDescription().length()<5){
            errors.rejectValue("description", "Groupdescription.valid");
        }

    }
}
