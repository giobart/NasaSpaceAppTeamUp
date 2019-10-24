package com.giobart.teamup.web;

import com.giobart.teamup.model.User;
import com.giobart.teamup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminControllerImpl extends BaseController {
    @Autowired
    private UserService userService;

    @PostMapping("admin/toggleMentor")
    public String toggleMentor(Model model, String username){
        User u = this.getUser();
        userService.toggleMentor(u,username);
        return "redirect:/welcome";
    }


}
