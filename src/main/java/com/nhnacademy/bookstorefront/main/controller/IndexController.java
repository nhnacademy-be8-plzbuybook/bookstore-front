package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.PagedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class IndexController {
    private final AuthenticationClient authenticationClient;

    public IndexController(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @GetMapping("/index")
    public String index(
            @RequestParam(defaultValue = "0") int page,        // 페이지 번호
            @RequestParam(defaultValue = "12") int size,       // 한 페이지당 아이템 수
            @RequestParam(defaultValue = "sellingBookId") String sortBy, // 정렬 기준
            @RequestParam(defaultValue = "desc") String sortDir, // 정렬 방향
            Model model) {
//        // 쇼핑몰 서버에서 도서 데이터 가져오기
//        List<BookDetailResponseDto> books = authenticationClient.getBooks(page, size, sortBy, sortDir);
//        // 디버깅 로그
//        books.forEach(book -> {
//            log.debug("Selling Book ID : {}", book.getBookId());
//            log.debug("Book Title : {}",  book.getBookTitle());
//            log.debug("Image URL : {}", book.getImageUrl());
//        });
//        model.addAttribute("books", books);
        // Feign 클라이언트를 통해 페이징된 데이터 요청
        PagedResponse<BookDetailResponseDto> response = authenticationClient.getBooks(page, size, sortBy, sortDir);

        model.addAttribute("books", response.getContent());
        model.addAttribute("currentPage", response.getNumber());
        model.addAttribute("totalPages", response.getTotalPages());

        return "index"; // index.html 렌더링
    }
}
