package com.giobart.teamup.web;

import com.giobart.teamup.model.Group;
import com.giobart.teamup.repository.GroupRepository;
import com.giobart.teamup.repository.UserRepository;
import com.giobart.teamup.service.GroupService;
import com.giobart.teamup.service.SecurityService;
import com.giobart.teamup.service.UserService;
import com.giobart.teamup.validator.GroupValidator;
import com.giobart.teamup.validator.UserValidator;
import com.giobart.teamup.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupValidator groupValidator;

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

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @PostMapping(value = "/welcome")
    public ModelAndView registration(@Valid @ModelAttribute("group") Group group, BindingResult bindingResult, Model model) {
        groupValidator.validate(group,bindingResult);

        renderPage(model,null,null);

        if (bindingResult.hasErrors()) {
            return new ModelAndView("welcome");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            User u = userService.findByUsername(((UserDetails) principal).getUsername());
            groupService.save(group,u);
        }

        return new ModelAndView("redirect:welcome");
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model,String joinGroup,String leaveGroup) {

        model.addAttribute("group", new Group());

        renderPage(model,joinGroup,leaveGroup);

        return "welcome";
    }

    private void renderPage(Model model,String joinGroup,String leaveGroup){

        User u = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            u = userService.findByUsername(((UserDetails) principal).getUsername());
            model.addAttribute("name",u.getName());
            model.addAttribute("surname",u.getSurname());
            model.addAttribute("email",u.getEmail());
            model.addAttribute("skills",u.getSkills());

            //join group if requested
            if(joinGroup!=null){
                groupService.joinGroup(joinGroup,u.getUsername());
            }

            //leave group if requested
            if(leaveGroup!=null){
                groupService.removeFromGroup(u.getUsername());
            }

        }

        //Available groups
        model.addAttribute("groupsAvailables",groupService.getAllAvailableGroups());

        //showing users
        model.addAttribute("users",userService.getAllUsers());

        //Your Group
        model.addAttribute("groupinfo", u != null ? u.getGroup() : null);

        //full groups
        model.addAttribute("groupsFull",groupService.getAllAvailableGroups());
    }
}
