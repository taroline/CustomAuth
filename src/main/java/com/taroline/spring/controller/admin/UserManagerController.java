package com.taroline.spring.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.taroline.spring.entity.User;
import com.taroline.spring.service.UserService;

@Controller
public class UserManagerController {

    @Autowired
    UserService userService;

    @GetMapping("/admin/user-manager")
    public String userManager(Model model){
        Iterable<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "admin/user-manager";
    }
}
