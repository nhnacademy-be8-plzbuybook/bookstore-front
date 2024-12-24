package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.MemberDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import com.nhnacademy.bookstorefront.main.service.CookieService;
import com.nhnacademy.bookstorefront.main.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class OauthLoginController {
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;
    private final CookieService cookieService;

    @GetMapping("/oauth/callback")
    public String processOauthLogin(@RequestParam String code, HttpServletResponse response, RedirectAttributes redirectAttributes) {

        //oauth 로그인 수행
        MemberDto memberDto = authenticationService.processOauthLogin(code);

        // 로그인 성공 시 액세스토큰 발급
        String accessToken = tokenService.issueAccessToken(memberDto);

        // 발급된 토큰 쿠키에 저장
        cookieService.addCookie(response, "accessToken", accessToken, 100000);

        redirectAttributes.addFlashAttribute("message", "로그인 성공");
        return "redirect:/";
    }
}
