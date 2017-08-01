package com.taroline.spring.controller.admin;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    /**
     * ユーザー一覧画面
     *
     * @param model
     * @return
     */
    @GetMapping("/admin/user-manager")
    public String userManager(Model model) {
        Iterable<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "admin/user-manager";
    }

    /**
     * ユーザー編集画面
     *
     * @param model
     * @param userForm
     * @param id
     * @param principal
     * @return
     */
    @GetMapping("/admin/user-manager-detail")
    public String userManagerDetail(Model model, @ModelAttribute UserForm userForm, @RequestParam("id") String id, Principal principal) {

        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("パラメータ: idは必須です");
        }

        // idをもとにDBからユーザーを取得 TODO: ロジッククラスとして、コントローラクラスからは切り離す
        User user = userService.findUserById(Long.valueOf(id));
        if (user == null) {
            throw new RuntimeException("指定されたユーザーが見つかりませんでした");
        }

        userForm.setId(String.valueOf(user.getId()));
        userForm.setUsername(user.getUsername());
        userForm.setMailAddress(user.getMailAddress());
        userForm.setPassword("");
        userForm.setEnabled(user.isEnabled() ? UserStatus.ENABLED.getValue() : UserStatus.DISABLED.getValue());
        userForm.setIsAdmin(user.isAdmin() ? "1" : "");

        Authentication authentication = (Authentication) principal;
        User currentUser = (User) authentication.getPrincipal();

        // 本人の場合は管理者フラグと有効/無効には触らせない
        model.addAttribute("isMe", id.equals(String.valueOf(currentUser.getId())));

        return "admin/user-manager-detail";
    }

    /**
     * ユーザー更新処理
     *
     * @param model
     * @param userForm
     * @param bindingResult
     * @param principal
     * @return
     */
    @PostMapping(value = "/admin/user-manager-detail", params = "action=save")
    public String userManagerDetailSave(Model model, @ModelAttribute @Validated UserForm userForm, BindingResult bindingResult, Principal principal) {

        // バリデーション
        if (bindingResult.hasErrors()) {
            return "admin/user-manager-detail";
        }

        // 更新対象エンティティの取得
        User user = userService.findUserById(Long.valueOf(userForm.getId()));
        if (user == null) {
            throw new RuntimeException("指定されたユーザーが見つかりませんでした");
        }

        user.setUsername(userForm.getUsername());
        user.setMailAddress(userForm.getMailAddress());

        // 認証情報の取得 TODO: Utilに移す？
        Authentication authentication = (Authentication) principal;
        User currentUser = (User) authentication.getPrincipal();

        // 更新対象が自身かどうか確認。
        // 自身であれば、有効無効・管理者フラグは触らせない
        if (!userForm.getId().equals(String.valueOf(currentUser.getId()))) {
            user.setEnabled(UserStatus.ENABLED.getValue().equals(userForm.getEnabled()));
            user.setAdmin(AdminFlag.IS_ADMIN.getValue().equals(userForm.getIsAdmin()));
        }

        // パスワードは入力された場合だけチェックする
        if (StringUtils.isNotEmpty(userForm.getPassword())) {
            user = userService.setPassword(user, userForm.getPassword());
        }

        try {
            // ユーザーデータ更新
            userService.updateUser(user);
        }
        catch (DataIntegrityViolationException e) {
            // 整合性制約によるエラー
            model.addAttribute("error", true);
            return "admin/user-manager-detail";
        }

        // 一覧へ
        return "redirect:/admin/user-manager";
    }

    /**
     * ユーザー編集キャンセル処理
     *
     * @return
     */
    @PostMapping(value = "/admin/user-manager-detail", params = "action=cancel")
    public String userManagerDetailCancel() {
        // 一覧へ
        return "redirect:/admin/user-manager";
    }
}
