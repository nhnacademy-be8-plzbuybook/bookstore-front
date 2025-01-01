package com.nhnacademy.bookstorefront.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/auth/verify-code")
    public String verifyCodePage(){
        return "/auth/verifyCodePage";
    }



}
