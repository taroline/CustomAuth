package com.taroline.spring.controller.admin;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taroline.spring.common.CommonConstant.AdminFlag;
import com.taroline.spring.common.CommonConstant.UserStatus;
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
    public String userManagerDetail(Model model, @ModelAttribute UserForm userForm, @RequestParam("id") String id, Principal principal) {
        if(StringUtils.isEmpty(id)) {
            throw new RuntimeException("パラメータ: idは必須です");
        }
        User user = userService.findUserById(Long.valueOf(id));
        if(user == null) {
            throw new RuntimeException("指定されたユーザーが見つかりませんでした");
        }

        userForm.setId(String.valueOf(user.getId()));
        userForm.setUsername(user.getUsername());
        userForm.setMailAddress(user.getMailAddress());
        userForm.setPassword("");
        userForm.setEnabled(user.isEnabled() ? UserStatus.ENABLED.getValue() : UserStatus.DISABLED.getValue());
        userForm.setIsAdmin(user.isAdmin() ? "1" : "");

        Authentication authentication = (Authentication)principal;
        User currentUser = (User)authentication.getPrincipal();

        // デッドロック防止のため本人の場合はEnabled,Disabledと管理者フラグには触らせない。
        model.addAttribute("isMe", id.equals(String.valueOf(currentUser.getId())));

        return "admin/user-manager-detail";
    }

    @PostMapping(value="/admin/user-manager-detail", params="action=save")
    public String userManagerDetailSave(Model model, @ModelAttribute @Validated UserForm userForm, BindingResult bindingResult) {
        User user = userService.findUserById(Long.valueOf(userForm.getId()));
        if(user == null) {
            throw new RuntimeException("指定されたユーザーが見つかりませんでした");
        }

        user.setUsername(userForm.getUsername());
        user.setMailAddress(userForm.getMailAddress());
        user.setEnabled(UserStatus.ENABLED.getValue().equals(userForm.getEnabled()));
        user.setAdmin(AdminFlag.IS_ADMIN.getValue().equals(userForm.getIsAdmin()));
        userService.updateUser(user);

        return "redirect:/admin/user-manager";
    }

    @PostMapping(value="/admin/user-manager-detail", params="action=cancel")
    public String userManagerDetailCancel() {
        return "redirect:/admin/user-manager";
    }
}
