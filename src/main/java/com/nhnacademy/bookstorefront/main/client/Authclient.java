package com.nhnacademy.bookstorefront.main.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "GATEWAY-DEV2")
public interface Authclient {
    @GetMapping("/api/auth/access-token/re-issue")
    String reIssueAccessToken();
}
