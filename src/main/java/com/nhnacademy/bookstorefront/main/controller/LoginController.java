package com.nhnacademy.bookstorefront.main.controller;

import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final RestTemplate restTemplate;

    public LoginController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        // 인증 서버 URL (게이트웨이를 통해 전달됨)
        String authenticationServerUrl = "http://localhost:8080/api/login";

        // 요청 헤더와 바디 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(credentials, headers);

        // 인증 서버로 요청 전달
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(authenticationServerUrl, request, String.class);
            return ResponseEntity.ok(response.getBody()); // 성공적으로 토큰 반환
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials"); // 실패 시 에러 메시지 반환
        }
    }
}
