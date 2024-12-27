package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.auth.LoginResponseDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import com.nhnacademy.bookstorefront.main.service.CookieService;
import com.nhnacademy.bookstorefront.main.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final CookieService cookieService;

    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    @PostMapping("/login/process")
    public String loginProcess(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpServletResponse response,
                               RedirectAttributes redirectAttributes) {

        LoginRequestDto loginRequest = new LoginRequestDto(username, password);
        // 로그인 수행
        LoginResponseDto loginResponse = authenticationService.processLogin(loginRequest);
        String accessToken = loginResponse.accessToken();

        // 발급된 토큰 쿠키에 저장
        cookieService.addCookie(response, "accessToken", accessToken, 100000);

        redirectAttributes.addFlashAttribute("message", "로그인 성공!");
        return "redirect:/index";
    }

    // 로그인 상태 확인 api
    @GetMapping("/api/check-login")
    @ResponseBody
    public boolean checkLoginStatus(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        boolean loggedIn = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    loggedIn = true;
                    break;
                }
            }
        }
        return loggedIn;
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        Cookie cookie = new Cookie("accessToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/index";
    }

}
