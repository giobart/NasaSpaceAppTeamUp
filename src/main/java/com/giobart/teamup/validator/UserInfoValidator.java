package com.giobart.teamup.validator;

import com.giobart.teamup.model.User;
import com.giobart.teamup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserInfoValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        //email check
        if(user.getEmail()==null || user.getEmail().isEmpty()){
          user.setEmail(null);
        }else
        if (user.getEmail().length()>40 || user.getEmail().length()<5){
            errors.rejectValue("email", "Email.valid");
        }

        //description check
        if(user.getSkills()==null || user.getSkills().isEmpty()){
            user.setSkills(null);
        }else
        if (user.getSkills().length()>50 || user.getSkills().length()<5) {
            errors.rejectValue("skills", "Skills.valid");
        }

        //name check
        if(user.getName()==null || user.getName().isEmpty()){
            user.setName(null);
        }else
        if (user.getName().length()>20 || user.getName().length()<2) {
            errors.rejectValue("name", "Name.valid");
        }

        //surname check
        if(user.getSurname()==null || user.getSurname().isEmpty()){
            user.setSurname(null);
        }else
        if (user.getSurname().length()>20 || user.getSurname().length()<2) {
            errors.rejectValue("surname", "Surname.valid");
        }
    }
}
