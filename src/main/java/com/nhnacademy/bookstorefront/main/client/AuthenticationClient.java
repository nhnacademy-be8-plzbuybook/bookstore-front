package com.nhnacademy.bookstorefront.main.client;


import com.nhnacademy.bookstorefront.main.dto.LoginRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@FeignClient(name = "GATEWAY-DEV")
public interface AuthenticationClient {


 

    @PostMapping("/api/upload")
    ResponseEntity<String> uploadFiles(@RequestParam("file") List<MultipartFile> multipartFiles);

    @PostMapping("/api/auth/login")
    ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest);

}
