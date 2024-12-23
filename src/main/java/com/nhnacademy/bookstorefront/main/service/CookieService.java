package com.nhnacademy.bookstorefront.main.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    void addCookie(HttpServletResponse response, String key, String value, int expiry);
    void removeCookie(HttpServletRequest request, HttpServletResponse response, String key);
}
