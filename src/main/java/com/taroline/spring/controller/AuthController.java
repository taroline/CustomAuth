package com.taroline.spring.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.taroline.spring.form.SignUpForm;
import com.taroline.spring.service.UserService;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signupForm", new SignUpForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(Model model, @Valid SignUpForm signUpForm, BindingResult bindingResult,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("signupForm", signUpForm);
            return "signup";
        }

        try {
            userService.registerUser(signUpForm.getUsername(), signUpForm.getPassword(), signUpForm.getMailAddress());
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("signupError", true);
            return "signup";
        }

        try {
            request.login(signUpForm.getUsername(), signUpForm.getPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}
