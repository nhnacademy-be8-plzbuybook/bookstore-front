package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.auth.OauthLoginResponseDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import com.nhnacademy.bookstorefront.main.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class OauthLoginController {
    private final AuthenticationService authenticationService;
    private final CookieService cookieService;

    @GetMapping("/oauth/callback")
    public String processOauthLogin(@RequestParam String code, HttpServletResponse response, RedirectAttributes redirectAttributes) {

        //oauth 로그인 수행
        OauthLoginResponseDto oauthLoginResponseDto = authenticationService.processOauthLogin(code);

        // 발급된 토큰 쿠키에 저장
        String accessToken = oauthLoginResponseDto.accessToken();
        cookieService.addCookie(response, "accessToken", accessToken, 100000);
        //TODO: 쿠키발급만료시간 설정

        redirectAttributes.addFlashAttribute("message", "로그인 성공");
        return "redirect:/";
    }
}
