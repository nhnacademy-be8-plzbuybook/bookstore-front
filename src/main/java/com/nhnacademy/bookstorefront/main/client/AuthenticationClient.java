package com.nhnacademy.bookstorefront.main.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@FeignClient(name = "gateway-dev")
public interface AuthenticationClient {

    @PostMapping("/api/login")
    ResponseEntity<String> login(@RequestBody Map<String, String> credentials);

    @PostMapping("/api/upload")
    ResponseEntity<String> uploadFiles(@RequestParam("file") List<MultipartFile> multipartFiles);

}
