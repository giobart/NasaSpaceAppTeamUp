package com.giobart.teamup.web;

import com.giobart.teamup.model.User;
import com.giobart.teamup.repository.RoleRepository;
import com.giobart.teamup.service.GroupService;
import com.giobart.teamup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

public abstract class BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoleRepository roleRepository;

    protected User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = null;

        if (principal instanceof UserDetails) {
            u = userService.findByUsername(((UserDetails) principal).getUsername());
        }

        if(u==null){
            throw new IllegalArgumentException("not valid user");
        }
        return u;
    }

    protected void renderPage(Model model){

        User u = getUser();

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

        //Available groups
        model.addAttribute("groupsAvailables",groupService.getAllAvailableGroups(u));

        //showing users
        model.addAttribute("users",userService.getAllUsers());

        //Your Group
        model.addAttribute("groupinfo", u != null ? u.getGroup() : null);

    }
}
