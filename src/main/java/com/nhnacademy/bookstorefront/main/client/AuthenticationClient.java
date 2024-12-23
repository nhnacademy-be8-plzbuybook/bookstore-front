package com.nhnacademy.bookstorefront.main.client;


import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "GATEWAY-DEV")
public interface AuthenticationClient {

    @PostMapping("/api/auth/login")
    ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest);
}
