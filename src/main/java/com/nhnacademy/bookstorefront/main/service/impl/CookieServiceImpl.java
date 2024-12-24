package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.service.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieServiceImpl implements CookieService {
    @Override
    public void addCookie(HttpServletResponse response, String key, String value, int expiry) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(expiry);

        response.addCookie(cookie);
    }

    @Override
    public void removeCookie(HttpServletRequest request, HttpServletResponse response, String key) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies) {
            if (cookie.getName().equals(key)) {
                cookie.setMaxAge(0);

                response.addCookie(cookie);
                return;
            }
        }
    }
}
