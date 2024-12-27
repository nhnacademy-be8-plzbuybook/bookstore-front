package com.nhnacademy.bookstorefront.main.client;


import com.nhnacademy.bookstorefront.main.dto.*;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateResponseDto;
import com.nhnacademy.bookstorefront.main.dto.auth.LoginResponseDto;
import com.nhnacademy.bookstorefront.main.dto.auth.OauthLoginResponseDto;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "GATEWAY")
public interface AuthenticationClient {

    @PostMapping("/api/upload")
    ResponseEntity<String> uploadFiles(@RequestParam("file") List<MultipartFile> multipartFiles);

    @GetMapping("/api/books")
    List<BookDetailResponseDto> getBooks();

    @PostMapping("/api/auth/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest);

//    @GetMapping("/api/auth/oauth/login")
//    ResponseEntity<?> oauthLogin(@RequestParam("provider") String provider);

    @PostMapping("/api/auth/access-token/issue")
    ResponseEntity<AccessTokenResponseDto> issueAccessToken(@RequestBody MemberDto memberDto);

    @GetMapping("/api/auth/oauth/login")
    ResponseEntity<OauthLoginResponseDto> getEmailFromOauthUser(@RequestParam String code);

    //마이 페이지에 필요한 정보 가져오기
    @GetMapping("/api/members/my/email")
    ResponseEntity<MyPageDto> getMemberMyPage();

    //회원 가입
    @PostMapping("/api/members")
    ResponseEntity<MemberCreateResponseDto> createMember(@RequestBody MemberCreateRequestDto memberCreateRequestDto);
}
