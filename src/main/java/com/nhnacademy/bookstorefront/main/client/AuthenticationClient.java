package com.nhnacademy.bookstorefront.main.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@FeignClient(name = "gateway-dev", url = "http://localhost:8080/api")
public interface AuthenticationClient {

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody Map<String, String> credentials);

//    @GetMapping("/auth/access-token/re-issue")
//    String reIssueAccessToken();
}
