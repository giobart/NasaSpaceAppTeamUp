package com.giobart.teamup.web;

import com.giobart.teamup.model.Group;
import com.giobart.teamup.repository.GroupRepository;
import com.giobart.teamup.repository.UserRepository;
import com.giobart.teamup.service.GroupService;
import com.giobart.teamup.service.SecurityService;
import com.giobart.teamup.service.UserService;
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
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

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

    @RequestMapping(value = "/groupregistration", method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("group") Group group, BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/welcome";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            User u = userService.findByUsername(((UserDetails) principal).getUsername());
            groupService.save(group,u);
        }

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
    public String welcome(Model model,String joinGroup,String leaveGroup, String GroupName, String GroupDescription) {

        User u = null;

        model.addAttribute("group", new Group());

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

            //create group if requested
            if(GroupName != null && GroupDescription !=null){
                Group group = new Group();
                group.setDescription(GroupDescription);
                group.setName(GroupName);
                groupService.save(group,u);
            }

        }


        //Available groups
        model.addAttribute("groupsAvailables",groupRepository.findAll().stream().filter(group -> group.getGroupmates().size()<6).collect(Collectors.toList()));

        //showing users
        model.addAttribute("users",userRepository.findAll());

        //Your Group
        model.addAttribute("groupinfo", u != null ? u.getGroup()!= null ? u.getGroup() : null : null);

        //full groups
        model.addAttribute("groupsFull",groupRepository.findAll().stream().filter(group -> group.getGroupmates().size()==5).collect(Collectors.toList()));


        return "welcome";
    }
}
