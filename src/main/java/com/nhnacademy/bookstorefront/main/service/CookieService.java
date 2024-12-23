package com.nhnacademy.bookstorefront.main.service;

import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    void addOnCookie(HttpServletResponse response,String key, String value, int expiry);
}
