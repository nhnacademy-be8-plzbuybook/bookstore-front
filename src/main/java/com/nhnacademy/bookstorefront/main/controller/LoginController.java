package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.MemberDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import com.nhnacademy.bookstorefront.main.service.CookieService;
import com.nhnacademy.bookstorefront.main.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class LoginController {
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
        MemberDto memberDto = authenticationService.processLogin(loginRequest);

        // 토근 발급
        String accessToken = tokenService.issueAccessToken(memberDto);

        // 발급된 토큰 쿠키에 저장
        cookieService.addOnCookie(response, "accessToken", accessToken, 100000);

        redirectAttributes.addFlashAttribute("message", "로그인 성공!");
        return "redirect:/";
    }
}
