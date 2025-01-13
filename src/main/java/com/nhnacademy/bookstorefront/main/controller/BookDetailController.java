package com.nhnacademy.bookstorefront.main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.client.MemberClient;
import com.nhnacademy.bookstorefront.main.dto.AdminSellingBookRegisterDto;
import com.nhnacademy.bookstorefront.main.dto.BookDetailResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.BookTagResponseDto;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import com.nhnacademy.bookstorefront.main.dto.order.OrderDetail;
import com.nhnacademy.bookstorefront.main.dto.review.ReviewCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.review.ReviewResponseDto;
import com.nhnacademy.bookstorefront.main.dto.review.ReviewUpdateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.review.ReviewWithReviewImageDto;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import com.nhnacademy.bookstorefront.main.service.OrderService;
import feign.FeignException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Controller
@RequiredArgsConstructor

public class BookDetailController {
    private final BookClient bookClient;
    private final MemberClient memberClient;
    private final AuthenticationService authenticationService;
    private final AuthenticationClient authenticationClient;
    private final OrderService orderService;




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
    public String getBookDetail(@PathVariable Long sellingBookId, Model model,HttpServletRequest request,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "2") int size) {
        // 쇼핑몰 서버에서 특정 책 데이터 가져오기
        BookDetailResponseDto bookDetail = bookClient.getSellingBook(sellingBookId);
        List<BookTagResponseDto> bookTagResponseDto = bookClient.getBookTagsByBookId(bookDetail.getBookId()).getBody();
        boolean isLoggedIn = authenticationService.isLoggedIn(request);
        log.info("bookDetail: {}", bookDetail);
        String role = null;
        Long memberId = null;
        String memberEmail = null;

        if(isLoggedIn) {
            String token = getTokenFromCookies(request);
            if(token != null) {
                role = authenticationClient.getRoleFromToken("Bearer " + token).getBody();
                MyPageDto myPageDto = authenticationService.getMyPage();
                memberId = myPageDto.getMemberId();
                memberEmail = myPageDto.getEmail();
            }
        }



        Page<ReviewWithReviewImageDto> reviews = bookClient.getReviewsByBookId(sellingBookId, page, size).getBody();

        List<ReviewWithReviewImageDto> userReviews = new ArrayList<>();
        if (isLoggedIn) {
            for (ReviewWithReviewImageDto review : reviews.getContent()) {
                if (review.getEmail().equals(memberEmail)) {
                    userReviews.add(review);
                }
            }
        }

        Double avgScore = bookClient.getAverageReview(sellingBookId);
        String avg = String.format("%.2f", avgScore);

        model.addAttribute("book", bookDetail);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("role", role);
        model.addAttribute("memberId", memberId);
        model.addAttribute("memberEmail", memberEmail);
        model.addAttribute("bookTags", bookTagResponseDto);
        model.addAttribute("reviews", reviews);
        model.addAttribute("userReviews", userReviews); // 자신이 작성한 리뷰만 전달
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("totalPages", reviews.getTotalPages()); // 전체 페이지 수
        model.addAttribute("size", size);
        model.addAttribute("avgScore", avg);


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

    @PostMapping("/book/detail/review")
    public String makeReview(@RequestParam Long memberId,
                             @RequestParam Long sellingBookId,
                             @RequestParam int score,
                             @RequestParam String content,
                             @RequestParam(required = false) List<MultipartFile> images,
                             Model model) {

        try {

            if (images != null) {
                images.removeIf(image -> image.isEmpty() || image.getOriginalFilename().isEmpty());
            }

            if (images == null || images.isEmpty()) {
                images = null;
            }


            ReviewCreateRequestDto reviewCreateRequestDto = new ReviewCreateRequestDto(memberId,sellingBookId, score, content);
            String reviewRequestDtoJson = new ObjectMapper().writeValueAsString(reviewCreateRequestDto);

            ResponseEntity<ReviewResponseDto> response = bookClient.createReview(reviewRequestDtoJson, images);

            if (response.getStatusCode().is4xxClientError()) {
                return "error/review-error";
            }
        } catch (FeignException e) {
            if (e.status() == 400){
                model.addAttribute("message", "구매 확정된 책만 리뷰를 작성할 수 있습니다.");
                return "error/review-error";
            }
            if (e.status() == 403){
                model.addAttribute("message", "주문한 책만 리뷰를 작성할 수 있습니다.");
                return "error/review-error";
            }
            if (e.status() == 409) {
                model.addAttribute("message", "리뷰 작성은 한번만 가능합니다.");
                return "error/review-error";
            }

            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return "redirect:/book/detail/" + sellingBookId;
    }


    @PostMapping("/book/reviews/update/{reviewId}")
    public String updateReview(@PathVariable Long reviewId,
                               @RequestParam("score") Integer score,
                               @RequestParam("content") String content,
                               @RequestParam(required = false) List<MultipartFile> images,
                               @RequestParam("memberId") Long memberId,
                               @RequestParam("sellingBookId") Long sellingBookId,
                               RedirectAttributes redirectAttributes) {
        try{
            ResponseEntity<Object> response = bookClient.updateReview(reviewId, score, content, images);

            if (response.getStatusCode().is2xxSuccessful()) {
                redirectAttributes.addFlashAttribute("successMessage", "리뷰가 성공적으로 수정되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "리뷰 수정 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "리뷰 수정 권한이 없거나 오류가 발생했습니다.");
        }

        return "redirect:/book/detail/" + sellingBookId;
    }
}
