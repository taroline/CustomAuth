package com.taroline.spring.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.taroline.spring.form.SignUpForm;
import com.taroline.spring.service.UserService;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    /**
     * ユーザー登録画面
     *
     * @param signUpForm
     * @param model
     * @return
     */
    @GetMapping("/signup")
    public String signup(@ModelAttribute SignUpForm signUpForm, Model model) {
        return "signup";
    }

    /**
     * ユーザー登録処理
     *
     * @param model
     * @param signUpForm
     * @param bindingResult
     * @param request
     * @return
     */
    @PostMapping("/signup")
    public String signupPost(Model model, @ModelAttribute @Validated SignUpForm signUpForm, BindingResult bindingResult,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return signup(signUpForm, model);
        }

        try {
            userService.registerUser(signUpForm.getUsername(), signUpForm.getPassword(), signUpForm.getMailAddress());
        }
        catch (DataIntegrityViolationException e) {
            model.addAttribute("signupError", true);
            return "signup";
        }

        try {
            request.login(signUpForm.getUsername(), signUpForm.getPassword());
        }
        catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}
