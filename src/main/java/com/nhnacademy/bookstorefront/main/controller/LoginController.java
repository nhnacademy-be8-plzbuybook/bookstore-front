package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.MemberDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class LoginController {
    private final AuthenticationClient authenticationClient;

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
        ResponseEntity<MemberDto> responseEntity = authenticationClient.login(loginRequest);

        // 로그인 성공하면 토큰발급 API 요청
        if (responseEntity.getStatusCode().is4xxClientError()) {
            redirectAttributes.addFlashAttribute("message", "잘못된 아이디 또는 비밀번호입니다.");
            return "redirect:/login";
        }

        ResponseEntity<JSONObject> tokenResponse = authenticationClient.issueAccessToken(responseEntity.getBody());

        if (!tokenResponse.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("message", "토큰 발급이 실패했습니다.");
            return "redirect:/login";
        }

        // 토큰발급 성공하면 쿠키에 등록
        String accessToken = (String) Objects.requireNonNull(tokenResponse.getBody()).get("accessToken");
        addAccessTokenOnCookie(response, accessToken);

        redirectAttributes.addFlashAttribute("message", "로그인 성공!");
        return "redirect:/";
    }

    private void addAccessTokenOnCookie(HttpServletResponse response, String accessToken) {
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(1000000); // 액세스토큰 유효기간만큼

        response.addCookie(cookie);
    }
}
