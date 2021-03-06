package com.taroline.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    /**
     * Top画面(メニュー)
     * @return
     */
    @RequestMapping("/")
    public String menu() {
        return "menu";
    }

    /**
     * 管理者メニュー
     * @return
     */
    @GetMapping("/admin/menu")
    public String admin() {
        return "admin/menu";
    }

    /**
     * ログイン画面
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * ログイン処理
     * @return
     */
    @PostMapping("/login")
    public String loginPost() {
        return "redirect:/login-error";
    }

    /**
     * ログインエラー画面
     * @param model
     * @return
     */
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
