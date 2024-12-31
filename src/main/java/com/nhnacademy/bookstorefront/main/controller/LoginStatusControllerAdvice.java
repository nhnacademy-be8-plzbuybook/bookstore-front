package com.nhnacademy.bookstorefront.main.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class LoginStatusControllerAdvice {
    public boolean loggedIn(HttpServletRequest request) {
        Object loggedInAttribute = request.getSession().getAttribute("loggedIn");
        return loggedInAttribute != null && (boolean) loggedInAttribute;
    }
}
