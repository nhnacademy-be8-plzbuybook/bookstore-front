package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.MemberDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class OauthLoginController {
    private final AuthenticationClient authenticationClient;

    @GetMapping("/oauth/callback")
    public String processOauthLogin(@RequestParam String code, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        ResponseEntity<JSONObject> responseEntity = authenticationClient.getEmailFromOauthUser(code);
        JSONObject jsonObject = responseEntity.getBody();

        // 인증완료 됐는데 회원정보가 없을때
        if (responseEntity.getStatusCode() == HttpStatus.FORBIDDEN) {
            redirectAttributes.addFlashAttribute("message", "최초 회원가입이 필요합니다.");
            String email = (String) Objects.requireNonNull(jsonObject.get("email"));
            return "redirect:/members/signup?email=" + email;
        }

        MemberDto memberDto = new MemberDto((String) jsonObject.get("email"), (String) jsonObject.get("role"));
        ResponseEntity<JSONObject> jsonObjectResponseEntity = authenticationClient.issueAccessToken(memberDto);

        if (!jsonObjectResponseEntity.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("message", "로그인 중 에러가 발생했습니다.");
            return "redirect:/login";
        }

        String accessToken = (String) Objects.requireNonNull(jsonObjectResponseEntity.getBody()).get("accessToken");
        addAccessTokenOnCookie(response, accessToken);

        redirectAttributes.addFlashAttribute("message", "로그인 성공");
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
