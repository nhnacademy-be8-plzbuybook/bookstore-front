package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final AuthenticationClient authenticationClient;

    public LoginController(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
//        try {
//            // Feign Client를 통해 인증 서버로 요청 전달
//            ResponseEntity<String> response = authenticationClient.login(credentials);
//            return ResponseEntity.ok(response.getBody()); // 성공적으로 토큰 반환
//        } catch (Exception e) {
//            return ResponseEntity.status(401).body("Invalid credentials"); // 실패 시 에러 메시지 반환
//        }
//    }
}
