package com.nhnacademy.bookstorefront.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping
    public String OrderReceipt() {
        return "order/receipt";
    }
}
