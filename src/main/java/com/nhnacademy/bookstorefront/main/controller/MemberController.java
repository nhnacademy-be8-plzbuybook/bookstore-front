package com.nhnacademy.bookstorefront.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    @GetMapping("/members/signup")
    public String signup(@RequestParam(value = "email", required = false) String email, Model model) {
        model.addAttribute("email", email);
        return "member/signup";
    }
}
