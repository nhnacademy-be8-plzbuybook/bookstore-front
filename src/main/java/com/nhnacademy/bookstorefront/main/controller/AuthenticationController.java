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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final CookieService cookieService;

    @Value("${oauth.payco.loginUrl}")
    private String PAYCO_LOGIN_URL;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("paycoLoginUrl", PAYCO_LOGIN_URL);
        return "loginPage";
    }

    @PostMapping("/login/process")
    public String loginProcess(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpServletResponse response,
                               Model model) {

        LoginRequestDto loginRequest = new LoginRequestDto(username, password);
            // 로그인 수행
            LoginResponseDto loginResponse = authenticationService.processLogin(loginRequest);

        if ("DORMANT".equals(loginResponse.memberStateName())) {
            return "redirect:" + loginResponse.redirectUrl();
        }

            String accessToken = loginResponse.accessToken();
            String role = loginResponse.role();

            // 발급된 토큰 쿠키에 저장
            cookieService.addCookie(response, "accessToken", accessToken, 100000);

//            redirectAttributes.addFlashAttribute("message", "로그인 성공!");
//            redirectAttributes.addFlashAttribute("role", role);
        model.addAttribute("role", role);
            return "redirect:/index";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션 무효화
        request.getSession().invalidate();

        // accessToken 쿠키 삭제
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setMaxAge(0); // 즉시 만료
        cookie.setPath("/"); // 경로를 동일하게 설정
        response.addCookie(cookie); // 응답에 쿠키 추가

        return "redirect:/index";
    }

    @GetMapping("/api/check-login")
    @ResponseBody
    public boolean checkLoginStatus(HttpServletRequest request) {
        return authenticationService.isLoggedIn(request);
    }


}
