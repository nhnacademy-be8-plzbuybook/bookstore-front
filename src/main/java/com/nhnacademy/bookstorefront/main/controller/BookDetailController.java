package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
@RequiredArgsConstructor

public class BookDetailController {
    private final BookClient bookClient;
    private final MemberClient memberClient;


    @GetMapping("/book/detail/{sellingBookId}")
    public String getBookDetail(@PathVariable Long sellingBookId, Model model) {
        // 쇼핑몰 서버에서 특정 책 데이터 가져오기
        BookDetailResponseDto bookDetail = bookClient.getSellingBook(sellingBookId);

        log.info("bookDetail: {}", bookDetail);

        model.addAttribute("book", bookDetail);

        return "detailview";
    }

    @PostMapping("/book/detail/like/{sellingBookId}")
    public String toggleLike(@PathVariable Long sellingBookId, RedirectAttributes redirectAttributes, Model model) {
        log.info("좋아요 요청 시작 - 판매책 ID: {}", sellingBookId);

        try {
            // 좋아요 처리 로직 호출
          ResponseEntity<Long> likesCount = memberClient.toggleLike(sellingBookId);

            // 성공 메시지와 좋아요 수 전달
            redirectAttributes.addFlashAttribute("success", "좋아요가 반영되었습니다.");
            redirectAttributes.addFlashAttribute("likesCount", likesCount);
        } catch (Exception e) {
            log.error("좋아요 처리 중 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "좋아요 처리 중 문제가 발생했습니다.");
        }
        BookDetailResponseDto bookDetail = bookClient.getSellingBook(sellingBookId);
        model.addAttribute("book", bookDetail);

        // 상세 페이지로 리다이렉트
        return "redirect:/book/detail/" + sellingBookId;
    }

}
