package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final AuthenticationService authenticationService;

    //테스트용
    private final AuthenticationClient authenticationClient;

    @GetMapping("/mypage")
    public String mypage(Model model) {
        MyPageDto myPageDto = authenticationService.getMyPage();
        //테스트용
        List<BookDetailResponseDto> books = authenticationClient.getBooks();
        model.addAttribute("books", books);


        model.addAttribute("member", myPageDto);

        return "member/mypage";
    }
}

