package com.giobart.teamup.validator;

import com.giobart.teamup.model.User;
import com.giobart.teamup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        //username check
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        //password check
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

        //email check
        if (user.getEmail().length()>40 || user.getEmail().length()<5){
            errors.rejectValue("email", "Email.valid");
        }

        //description check
        if (user.getSkills().length()>50 || user.getSkills().length()<5) {
            errors.rejectValue("skills", "Skills.valid");
        }

        //name check
        if (user.getName().length()>20 || user.getName().length()<2) {
            errors.rejectValue("name", "Name.valid");
        }

        //surname check
        if (user.getSurname().length()>20 || user.getSurname().length()<2) {
            errors.rejectValue("surname", "Surname.valid");
        }

        //degree check
        if (user.getDegreeCourse().length()>30 || user.getSurname().length()<2) {
            errors.rejectValue("degreeCourse", "Degree.valid");
        }
    }
}
