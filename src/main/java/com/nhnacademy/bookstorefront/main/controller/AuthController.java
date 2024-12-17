package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.Authclient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final Authclient authclient;

    public AuthController(Authclient authclient) {
        this.authclient = authclient;
    }

    @PostMapping("/api/auth/re-issue")
    public String reIssueAccessToken() {
        // Feign 클라이언트를 통해 인증서버로 액세스 토큰 재발급 요청
        return authclient.reIssueAccessToken();
    }
}
