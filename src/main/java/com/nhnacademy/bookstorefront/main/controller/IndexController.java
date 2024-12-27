package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    private final AuthenticationClient authenticationClient;

    public IndexController(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @GetMapping("/index")
    public String index(Model model) {
        // 쇼핑몰 서버에서 도서 데이터 가져오기
        List<BookDetailResponseDto> books = authenticationClient.getBooks();
        // 디버깅 로그
        books.forEach(book -> {
            System.out.println("Book ID: " + book.getBookId());
            System.out.println("Book Title: " + book.getBookTitle());
            System.out.println("Image URL: " + book.getImageUrl());
        });
        model.addAttribute("books", books);

        return "index"; // index.html 렌더링
    }
}
