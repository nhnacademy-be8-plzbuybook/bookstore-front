package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class BookDetailController {
    private final BookClient bookClient;
    public BookDetailController(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    @GetMapping("/book/detail/{sellingBookId}")
    public String getBookDetail(@PathVariable Long sellingBookId, Model model) {
        // 쇼핑몰 서버에서 특정 책 데이터 가져오기
        BookDetailResponseDto bookDetail = bookClient.getSellingBook(sellingBookId);

        log.info("bookDetail: {}", bookDetail);

        model.addAttribute("book", bookDetail);

        return "detailview";
    }

    @PostMapping("/book/detail/like/{sellingBookId}")
    public ResponseEntity<Long> toggleLike(@PathVariable Long sellingBookId,
                             @RequestHeader("X-USER-ID") String memberEmail,
                             RedirectAttributes redirectAttributes) {
        log.info("좋아요 요청 - 판매책 ID: {}, 회원 이메일: {}", sellingBookId, memberEmail);

        // 게이트웨이를 통해 좋아요 요청
        Long likeCount = bookClient.toggleLike(sellingBookId, memberEmail).getBody();

        // 좋아요 수를 리다이렉트 시 전달
        redirectAttributes.addFlashAttribute("likeCount", likeCount);
        return "redirect:/book/detail/" + sellingBookId;
    }
}
