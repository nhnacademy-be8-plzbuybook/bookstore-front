package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationClient authenticationClient;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_KEY_PREFIX = "fronteend:auth:";

    @GetMapping("/auth/verify-code")
    public String verifyCodePage(){
        return "auth/verifyCodePage";
    }

    @PostMapping("/auth/request-code")
    public String requestVerificationCode(@RequestParam("userId") String userId, Model model) {
        try{
            //UUID를 생성하여 임시 토큰 생성
            String token = UUID.randomUUID().toString();
            String redisKey = REDIS_KEY_PREFIX + token;

            redisTemplate.opsForValue().set(redisKey, userId, 5, TimeUnit.MINUTES);

            String response = authenticationClient.requestVerificationCode(userId);
            model.addAttribute("message", response);
            model.addAttribute("token", token);
        }catch(Exception e){
            model.addAttribute("message", "인증 코드 요청에 실패 했습니다.");
        }
        return "auth/verifyCodePage";
    }


        @PostMapping("/auth/verify-code")
    public String verifyCode(@RequestParam("code") String code, @RequestParam("token") String token, Model model) {
        try{
            String response = authenticationClient.verifyVerificationCode(token, code);
            model.addAttribute("message", response);
        }catch(Exception e){
            model.addAttribute("message", "인증 코드 검증에 실패했습니다.");
        }

        return "auth/verifyCodePage";
    }




}
