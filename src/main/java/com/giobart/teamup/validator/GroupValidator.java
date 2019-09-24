package com.giobart.teamup.validator;

import com.giobart.teamup.model.Group;
import com.giobart.teamup.model.User;
import com.giobart.teamup.service.GroupService;
import com.giobart.teamup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GroupValidator implements Validator {

    @Autowired
    private GroupService groupService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Group group = (Group) o;

        //Groupname check
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (group.getName().length() > 15 || group.getName().length() < 4) {
            errors.rejectValue("name", "Groupname.valid");
        }

        //GroupDescription check
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (group.getName().length() > 50 || group.getName().length() < 1) {
            errors.rejectValue("description", "Groupdescription.valid");
        }

    }
}
