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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class GroupControllerImpl extends BaseController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupInfoValidator groupInfoValidator;

    @Autowired
    private GroupValidator groupValidator;

    @PostMapping("/group/joinGroup")
    public String joinGroup(Model model,String groupname) {

        model.addAttribute("group", new Group());
        groupService.joinGroup(groupname,this.getUser().getUsername());
        return "redirect:/welcome";
    }

    @PostMapping("/group/leaveGroup")
    public String leaveGroup(String groupname,Model model) {

        model.addAttribute("group", new Group());
        groupService.removeFromGroup(this.getUser().getUsername());
        return "redirect:/welcome";
    }

    @PostMapping("/group/followGroup")
    public String followGroup(Model model,String groupname) {

        model.addAttribute("group", new Group());
        groupService.followGroup(this.getUser(),groupname);
        return "redirect:/welcome";
    }

    @PostMapping("/group/unfollowGroup")
    public String unfollowGroup(Model model,String groupname) {

        model.addAttribute("group", new Group());
        groupService.unfollowGroup(this.getUser(),groupname);
        return "redirect:/welcome";
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

    @PostMapping(value = "/group/new")
    public ModelAndView registration(@Valid @ModelAttribute("group") Group group, BindingResult bindingResult, Model model) {
        groupValidator.validate(group,bindingResult);

        if (bindingResult.hasErrors()) {
            renderPage(model);
            return new ModelAndView("/welcome");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            User u = userService.findByUsername(((UserDetails) principal).getUsername());
            groupService.save(group,u);
        }

        return new ModelAndView("redirect:/welcome");
    }

}
