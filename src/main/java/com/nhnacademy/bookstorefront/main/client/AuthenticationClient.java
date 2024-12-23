package com.nhnacademy.bookstorefront.main.client;


import com.nhnacademy.bookstorefront.main.dto.AccessTokenResponseDto;
import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import com.nhnacademy.bookstorefront.main.dto.MemberDto;
import com.nhnacademy.bookstorefront.main.dto.OauthResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "GATEWAY-DEV")
public interface AuthenticationClient {

    @PostMapping("/api/auth/login")
    ResponseEntity<MemberDto> login(@RequestBody LoginRequestDto loginRequest);

    @GetMapping("/api/auth/oauth/login")
    ResponseEntity<?> oauthLogin(@RequestParam("provider") String provider);

    @PostMapping("/api/auth/access-token/issue")
    ResponseEntity<AccessTokenResponseDto> issueAccessToken(@RequestBody MemberDto memberDto);

    @GetMapping("/api/auth/oauth/callback")
    ResponseEntity<OauthResponseDto> getEmailFromOauthUser(@RequestParam String code);
}
