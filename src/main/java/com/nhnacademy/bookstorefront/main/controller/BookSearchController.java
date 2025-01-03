package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.BookSearchClient;
import com.nhnacademy.bookstorefront.main.dto.BookSearchPagedResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchClient bookSearchClient;


    @GetMapping("/search/books")
    public String searchBook(Model model,
                             @RequestParam String searchKeyword,
                             @RequestParam(defaultValue = "0") int page) {
        // API 호출 (ResponseEntity로 반환)
        BookSearchPagedResponseDto response = bookSearchClient.searchBook(searchKeyword, page, 3);

        // 응답 내용에서 필요한 데이터를 추출


        // 모델에 검색어와 결과 및 페이징 정보를 추가
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("searchResult", Objects.requireNonNull(response).getContent()); // 책 목록
        model.addAttribute("totalPages", response.getTotalPages()); // 전체 페이지 수
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("pageSize", 3); // 페이지 크기

        return "search/searchResult"; // 검색 결과 뷰
    }


}
