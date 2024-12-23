package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import feign.FeignException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationClient authenticationClient;

    @GetMapping
    public String home() {
        return "loginPage";
    }

    @PostMapping("/process")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes) {
        LoginRequestDto loginRequest = new LoginRequestDto(username, password);
        try {
            ResponseEntity<String> responseEntity = authenticationClient.login(loginRequest);
            String accessToken = responseEntity.getBody();
            addAccessTokenOnCookie(response, accessToken);
            return "redirect:/index";

        } catch (FeignException.BadRequest e) {
            redirectAttributes.addFlashAttribute("message", "잘못된 입력입니다.");
            return "redirect:/login";

        } catch (FeignException.Unauthorized e) {
            redirectAttributes.addFlashAttribute("message", "잘못된 아이디 또는 비밀번호입니다.");
            return "redirect:/login";
        }
    }

    private void addAccessTokenOnCookie(HttpServletResponse response, String accessToken) {
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(1000000); // 액세스토큰 유효기간만큼

        response.addCookie(cookie);
    }
}
