package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.crypto.SecretKey;
import java.util.Base64;

import static javax.crypto.Cipher.SECRET_KEY;

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
    public String toggleLike(@PathVariable Long sellingBookId,
                             @RequestParam("memberEmail") String memberEmail,
                             RedirectAttributes redirectAttributes) {
        log.info("좋아요 요청 시작 - 판매책 ID: {}, 사용자 이메일: {}", sellingBookId, memberEmail);

        try {
            // 좋아요 처리 로직 호출
            Long likeCount = bookClient.toggleLike(sellingBookId, memberEmail).getBody();

            // 성공 메시지와 좋아요 수 전달
            redirectAttributes.addFlashAttribute("success", "좋아요가 반영되었습니다.");
            redirectAttributes.addFlashAttribute("likeCount", likeCount);
        } catch (Exception e) {
            log.error("좋아요 처리 중 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "좋아요 처리 중 문제가 발생했습니다.");
        }

        // 상세 페이지로 리다이렉트
        return "redirect:/book/detail/" + sellingBookId;
    }

}
