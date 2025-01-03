package com.nhnacademy.bookstorefront.main.client;


import com.nhnacademy.bookstorefront.main.dto.*;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberCreateResponseDto;
import com.nhnacademy.bookstorefront.main.dto.Member.MemberModifyRequestDto;
import com.nhnacademy.bookstorefront.main.dto.auth.LoginResponseDto;
import com.nhnacademy.bookstorefront.main.dto.auth.OauthLoginResponseDto;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "GATEWAY", contextId = "authenticationClient")
public interface AuthenticationClient {

    @PostMapping("/api/upload")
    ResponseEntity<String> uploadFiles(@RequestParam("file") List<MultipartFile> multipartFiles);

//    @GetMapping("/api/selling-books")
//    Page<BookDetailResponseDto> getBooks(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "sellingBookId") String sortBy,
//            @RequestParam(defaultValue = "desc") String sortDir
//    );
//
//
//    @GetMapping("/api/selling-books/{sellingBookId}")
//    BookDetailResponseDto getSellingBook(@PathVariable("sellingBookId") Long sellingBookId);

    @PostMapping("/api/auth/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest);

//    @GetMapping("/api/auth/oauth/login")
//    ResponseEntity<?> oauthLogin(@RequestParam("provider") String provider);

    @PostMapping("/api/auth/access-token/issue")
    ResponseEntity<AccessTokenResponseDto> issueAccessToken(@RequestBody MemberDto memberDto);

    @GetMapping("/api/auth/oauth/login")
    ResponseEntity<OauthLoginResponseDto> oauthLogin(@RequestParam String code);

    //마이 페이지에 필요한 정보 가져오기
    @GetMapping("/api/members/my/email")
    ResponseEntity<MyPageDto> getMemberMyPage();

    //회원 가입
    @PostMapping("/api/members")
    ResponseEntity<MemberCreateResponseDto> createMember(@RequestBody MemberCreateRequestDto memberCreateRequestDto);


    //회원 정보 수정
    @PostMapping("/api/members/me")
    ResponseEntity<String> updateMember(@RequestBody MemberModifyRequestDto memberModifyRequestDto);

    // 회원 탈퇴
    @PostMapping("/api/members/withdrawal")
    ResponseEntity<String> withdrawState(@RequestBody WithdrawStateRequestDto withdrawStateRequestDto);

    //인증 코드 요청
    @PostMapping("/api/auth/request-code")
    String requestVerificationCode(@RequestParam("userId") String userId);

    //인증 코드 검증
    @PostMapping("/api/auth/verify-code")
    String verifyVerificationCode(@RequestParam("token") String token, @RequestParam("code") String code);

}