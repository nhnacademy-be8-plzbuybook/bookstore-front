package com.nhnacademy.bookstorefront.main.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@FeignClient(name = "gateway", url = "http://localhost:8080/api")
public interface AuthenticationClient {

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody Map<String, String> credentials);
}
