package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.dto.AdminSellingBookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.BookTagResponseDto;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import com.nhnacademy.bookstorefront.main.dto.review.ReviewCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.review.ReviewResponseDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor

public class BookDetailController {
    private final BookClient bookClient;
    private final MemberClient memberClient;
    private final AuthenticationService authenticationService;
    private final AuthenticationClient authenticationClient;




//    @GetMapping("/api/books/admin")
//    public String registerBookPage() {
//        return "admin/bookregister";
//    }


//    @PostMapping("/api/books/adminregister")
//    public String registerBook(AdminSellingBookRegisterDto adminSellingBookRegisterDto) {
//        bookClient.registerSellingBook(adminSellingBookRegisterDto); // bookClient를 통해 데이터 전송
//        return "redirect:/admin/bookList";
//    }


    @GetMapping("/book/detail/{sellingBookId}")
    public String getBookDetail(@PathVariable Long sellingBookId, Model model,HttpServletRequest request) {
        // 쇼핑몰 서버에서 특정 책 데이터 가져오기
        BookDetailResponseDto bookDetail = bookClient.getSellingBook(sellingBookId);
        List<BookTagResponseDto> bookTagResponseDto = bookClient.getBookTagsByBookId(bookDetail.getBookId()).getBody();
        boolean isLoggedIn = authenticationService.isLoggedIn(request);
        log.info("bookDetail: {}", bookDetail);
        String role = null;

        if(isLoggedIn) {
            String token = getTokenFromCookies(request);
            if(token != null) {
                role = authenticationClient.getRoleFromToken("Bearer " + token).getBody();
            }
        }


//        MyPageDto myPageDto = authenticationService.getMyPage();
//        Long memberId = myPageDto.getMemberId();

//        Long OrderProductId = bookClient.getOrderProductBySellingBookId(sellingBookId).getBody();

        model.addAttribute("book", bookDetail);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("role", role);
        model.addAttribute("bookTags", bookTagResponseDto);
//        model.addAttribute("memberId", memberId);
//        model.addAttribute("orderProductId", OrderProductId);


        return "detailview";
    }
    private String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
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

//    @PostMapping("/book/detail/review")
//    public ResponseEntity<ReviewResponseDto> makeReview(@RequestParam Long memberId,
//                                                          @RequestParam Long orderProductId,
//                                                          @RequestParam int score,
//                                                          @RequestParam String content) {
//        ReviewCreateRequestDto reviewCreateRequestDto = new ReviewCreateRequestDto(memberId, orderProductId, score, content);
//        ResponseEntity<ReviewResponseDto> responseEntity = bookClient.createReview(reviewCreateRequestDto);
//
//        return ResponseEntity.ok(responseEntity.getBody());
//    }

}
