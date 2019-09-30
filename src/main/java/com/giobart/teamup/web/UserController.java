package com.giobart.teamup.web;

import com.giobart.teamup.model.Group;
import com.giobart.teamup.repository.RoleRepository;
import com.giobart.teamup.service.GroupService;
import com.giobart.teamup.service.SecurityService;
import com.giobart.teamup.service.UserService;
import com.giobart.teamup.validator.GroupInfoValidator;
import com.giobart.teamup.validator.GroupValidator;
import com.giobart.teamup.validator.UserInfoValidator;
import com.giobart.teamup.validator.UserValidator;
import com.giobart.teamup.model.User;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserInfoValidator userInfoValidator;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupValidator groupValidator;

    @Autowired
    private GroupInfoValidator groupInfoValidator;

    @Autowired
    private RoleRepository roleRepository;

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

    @PostMapping(value = "/welcome")
    public ModelAndView registration(@Valid @ModelAttribute("group") Group group, BindingResult bindingResult, Model model) {
        groupValidator.validate(group,bindingResult);

        renderPage(model,null,null,null,null,null);

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
    public String welcome(Model model,String joinGroup,String leaveGroup,String followGroup, String unfollowGroup, String toogleUserMentor) {

        model.addAttribute("group", new Group());

        renderPage(model,joinGroup,leaveGroup,followGroup,unfollowGroup,toogleUserMentor);

        return "welcome";
    }


    @GetMapping("/groupinfo")
    public String groupinfo(Model model){
        model.addAttribute("groupInfo", new Group());

        User u = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            u = userService.findByUsername(((UserDetails) principal).getUsername());
            Group g = u.getGroup();
            model.addAttribute("groupName",g.getName());
            model.addAttribute("description",g.getDescription());
            model.addAttribute("telegramGroup",g.getTelegramGroup());
        }

        return "groupinformation";
    }

    @PostMapping("/groupinfo")
    public String groupinfo(@ModelAttribute("groupInfo") Group groupForm, BindingResult bindingResult, Model model){
        User u = null;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            u = userService.findByUsername(((UserDetails) principal).getUsername());
        }

        groupInfoValidator.validate(groupForm,bindingResult);

        if (bindingResult.hasErrors()) {
            u = userService.findByUsername(((UserDetails) principal).getUsername());
            Group g = u.getGroup();
            model.addAttribute("groupName",g.getName());
            model.addAttribute("description",g.getDescription());
            model.addAttribute("telegramGroup",g.getTelegramGroup());
            return "groupinformation";
        }

        groupService.update(groupForm,u);

        return "redirect:/welcome";
    }

    private void renderPage(Model model,String joinGroup,String leaveGroup,String followGroup, String unfollowGroup,String toggleUserMentor){

        User u = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            u = userService.findByUsername(((UserDetails) principal).getUsername());
            model.addAttribute("name",u.getName());
            model.addAttribute("surname",u.getSurname());
            model.addAttribute("email",u.getEmail());
            model.addAttribute("skills",u.getSkills());
            model.addAttribute("degreeCourse",u.getDegreeCourse());

            //if user is a mentor
            if(u.getRoles().contains(roleRepository.getOne(3l))){
                model.addAttribute("isMentor",true);
                model.addAttribute("followingGroups",u.getFollowingGroups());
            }else {
                model.addAttribute("isMentor", false);
            }

            //if user is admin
            if(u.getRoles().contains(roleRepository.getOne(1l))){
                model.addAttribute("isAdmin",true);
            }else {
                model.addAttribute("isAdmin", false);
            }

            //user join group if requested
            if(joinGroup!=null){
                groupService.joinGroup(joinGroup,u.getUsername());
            }

            //user leave group if requested
            if(leaveGroup!=null){
                groupService.removeFromGroup(u.getUsername());
            }

            //mentor follow group
            if(followGroup!=null){
                groupService.followGroup(u,followGroup);
            }

            //mentor unfollow group
            if(unfollowGroup!=null){
                groupService.unfollowGroup(u,unfollowGroup);
            }

            //toggle mentor
            if(toggleUserMentor!=null){
                userService.toggleMentor(u,toggleUserMentor);
            }

        }

        //Available groups
        model.addAttribute("groupsAvailables",groupService.getAllAvailableGroups(u));

        //showing users
        model.addAttribute("users",userService.getAllUsers());

        //Your Group
        model.addAttribute("groupinfo", u != null ? u.getGroup() : null);

    }
}
