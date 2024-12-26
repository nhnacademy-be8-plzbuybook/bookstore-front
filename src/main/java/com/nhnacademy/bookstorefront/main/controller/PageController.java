package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final AuthenticationService authenticationService;

    @GetMapping("/mypage")
    public String mypage(Model model) {
        MyPageDto myPageDto = authenticationService.getMyPage();


        model.addAttribute("member", myPageDto);

        return "member/mypage";
    }
}
