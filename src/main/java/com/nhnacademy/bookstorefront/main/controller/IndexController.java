package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import com.nhnacademy.bookstorefront.main.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final AuthenticationClient authenticationClient;
    private final AuthenticationService authenticationService;

    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model) {
        // AuthenticationService를 통해 로그인 상태 확인
        boolean isLoggedIn = authenticationService.isLoggedIn(request);

        // 쇼핑몰 서버에서 도서 데이터 가져오기
        List<BookDetailResponseDto> books = authenticationClient.getBooks();

        // 디버깅 로그
        books.forEach(book -> {
            log.debug("Selling Book ID : {}", book.getBookId());
            log.debug("Book Title : {}", book.getBookTitle());
            log.debug("Image URL : {}", book.getImageUrl());
        });

        // 모델에 데이터 추가
        model.addAttribute("books", books);
        model.addAttribute("isLoggedIn", isLoggedIn);

        return "index"; // index.html 렌더링
    }
}
