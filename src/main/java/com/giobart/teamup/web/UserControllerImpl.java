package com.giobart.teamup.web;

import com.giobart.teamup.model.Group;
import com.giobart.teamup.model.User;
import com.giobart.teamup.repository.RoleRepository;
import com.giobart.teamup.service.GroupService;
import com.giobart.teamup.service.SecurityService;
import com.giobart.teamup.service.UserService;
import com.giobart.teamup.validator.GroupInfoValidator;
import com.giobart.teamup.validator.GroupValidator;
import com.giobart.teamup.validator.UserInfoValidator;
import com.giobart.teamup.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserControllerImpl extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserInfoValidator userInfoValidator;



    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/accountinfo")
    public String accountinfo(Model model){
        model.addAttribute("userInfo", new User());

        User u = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            u = userService.findByUsername(((UserDetails) principal).getUsername());
            model.addAttribute("username",u.getUsername());
            model.addAttribute("name",u.getName());
            model.addAttribute("surname",u.getSurname());
            model.addAttribute("email",u.getEmail());
            model.addAttribute("roles",u.getRoles());
            model.addAttribute("degreeCourse",u.getDegreeCourse());
            model.addAttribute("skills",u.getSkills());
        }

        return "accountinformation";
    }

    @PostMapping("/accountinfo")
    public String accountinfo(@ModelAttribute("userInfo") User userForm, BindingResult bindingResult, Model model){
        User u = null;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            u = userService.findByUsername(((UserDetails) principal).getUsername());
        }

        userInfoValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors() || u==null) {
            model.addAttribute("username",u.getUsername());
            model.addAttribute("name",u.getName());
            model.addAttribute("surname",u.getSurname());
            model.addAttribute("email",u.getEmail());
            model.addAttribute("degreeCourse",u.getDegreeCourse());
            model.addAttribute("roles",u.getRoles());
            model.addAttribute("skills",u.getSkills());
            return "accountinformation";
        }

        userForm.setUsername(u.getUsername());
        userService.update(userForm);

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model,String toogleUserMentor) {

        model.addAttribute("group", new Group());

        renderPage(model);

        return "welcome";
    }

}
