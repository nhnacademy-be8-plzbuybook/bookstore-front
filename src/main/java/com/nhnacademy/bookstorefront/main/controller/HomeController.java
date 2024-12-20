package com.nhnacademy.bookstorefront.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String home() {
        return "loginPage";
    }

    @GetMapping("/payment")
    public String payment() {
        return "orders/payment";
    }
}
