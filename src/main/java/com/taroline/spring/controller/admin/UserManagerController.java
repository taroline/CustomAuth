package com.taroline.spring.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taroline.spring.CommonConstant.UserStatus;
import com.taroline.spring.entity.User;
import com.taroline.spring.form.UserForm;
import com.taroline.spring.service.UserService;

@Controller
public class UserManagerController {

    @Autowired
    UserService userService;

    @GetMapping("/admin/user-manager")
    public String userManager(Model model) {
        Iterable<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "admin/user-manager";
    }

    @GetMapping("/admin/user-manager-detail")
    public String userManagerDetail(Model model, @RequestParam("id") String id) {
        if(StringUtils.isEmpty(id)) {
            throw new RuntimeException("パラメータ: idは必須です");
        }
        User user = userService.findUserById(Long.valueOf(id));
        if(user == null) {
            throw new RuntimeException("指定されたユーザーが見つかりませんでした");
        }

        UserForm userForm = new UserForm();
        userForm.setId(String.valueOf(user.getId()));
        userForm.setMailAddress(user.getMailAddress());
        userForm.setPassword("");
        userForm.setEnabled(user.isEnabled() ? UserStatus.ENABLED.getValue() : UserStatus.DISABLED.getValue());
        userForm.setIsAdmin(user.isAdmin() ? "1" : "");

        model.addAttribute("userForm", userForm);
        return "admin/user-manager-detail";
    }
}
