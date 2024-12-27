package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BookDetailController {
    private final AuthenticationClient authenticationClient;

    public BookDetailController(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @GetMapping("/book/detail/{sellingBookId}")
    public String getBookDetail(@PathVariable Long sellingBookId, Model model) {
        // 쇼핑몰 서버에서 특정 책 데이터 가져오기
        BookDetailResponseDto bookDetail = authenticationClient.getSellingBook(sellingBookId);
        model.addAttribute("book", bookDetail);

        return "detailview";
    }
}
