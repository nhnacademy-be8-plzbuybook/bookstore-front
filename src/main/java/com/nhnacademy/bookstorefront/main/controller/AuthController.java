package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationClient authenticationClient;

    @GetMapping("/auth/verify-code")
    public String verifyCodePage(){
        return "/auth/verifyCodePage";
    }

    @PostMapping("/auth/request-code")
    public String requestVerificationCode(@RequestParam("userId") String userId, Model model, HttpSession session) {
        try{
            session.setAttribute("userId", userId);
            String response = authenticationClient.requestVerificationCode(userId);
            model.addAttribute("message", response);
        }catch(Exception e){
            model.addAttribute("message", "인증 코드 요청에 실패 했습니다.");
        }
        return "/auth/verifyCodePage";
    }

    @PostMapping("/auth/verify-code")
    public String verifyCode(@RequestParam("code") String code, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null){
            model.addAttribute("message", "사용자 id가 세션에 없다!");
            return "/auth/verifyCodePage";
        }
        try{
            String response = authenticationClient.verifyVerificationCode(userId, code);
            model.addAttribute("message", response);
        }catch(Exception e){
            model.addAttribute("message", "인증 코드 검증에 실패했습니다.");
        }

        return "/auth/verifyCodePage";
    }




}
