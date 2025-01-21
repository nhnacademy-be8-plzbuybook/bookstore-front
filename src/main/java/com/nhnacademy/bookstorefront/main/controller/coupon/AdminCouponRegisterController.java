package com.nhnacademy.bookstorefront.main.controller.coupon;

import com.nhnacademy.bookstorefront.main.client.BookClient;
import com.nhnacademy.bookstorefront.main.client.BookSearchClient;
import com.nhnacademy.bookstorefront.main.dto.BookSearchPagedResponseDto;
import com.nhnacademy.bookstorefront.main.dto.book.CategorySimpleResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponPolicyResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponPolicySaveRequestDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponTargetSaveRequestDto;
import com.nhnacademy.bookstorefront.main.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class AdminCouponRegisterController {
    private final CouponService couponService;
    private final BookClient bookClient;
    private final BookSearchClient bookSearchClient;

    // 쿠폰정책등록 페이지
    @GetMapping("/admin/coupon-register")
    public String couponRegister() {
        return "admin/coupon/coupon-register";
    }

    // 책 검색
    @GetMapping("/admin/coupon-book-search")
    public String searchBook(Model model, @RequestParam String searchKeyword,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "1") int pageSize) {

        BookSearchPagedResponseDto response = bookSearchClient.searchBook(searchKeyword, page, pageSize);

        model.addAttribute("searchKeyword", searchKeyword); // 검색내용
        model.addAttribute("searchResult", Objects.requireNonNull(response).getContent()); // 책 목록
        model.addAttribute("totalPages", response.getTotalPages()); // 전체 페이지 수
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("pageSize", pageSize); // 페이지 크기

        return "admin/coupon/coupon-book-search";
    }


    // 카테고리 검색
    @GetMapping("/admin/coupon-category-search")
    public String searchCategory(Model model, @RequestParam String searchKeyword,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "1") int size) {

        Page<CategorySimpleResponseDto> response = bookClient.searchCategories(searchKeyword, page, size).getBody();

        model.addAttribute("categories", response);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("searchResult", Objects.requireNonNull(response).getContent());
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "admin/coupon/coupon-category-search";
    }

    // 쿠폰정책 생성 및 쿠폰대상 생성
    @PostMapping("/admin/coupon-policies")
    public String createCouponPolicyAndTarget(
            @ModelAttribute @Valid CouponPolicySaveRequestDto couponPolicyRequestDto,
            @RequestParam(required = false) Long bookId,
            @RequestParam(required = false) Long categoryId,
            Model model) {

        CouponPolicyResponseDto policyResponse = couponService.createCouponPolicy(couponPolicyRequestDto);
        Long couponPolicyId = Objects.requireNonNull(policyResponse).id();

        if (bookId != null) {
            couponService.addCouponTargets(couponPolicyId, bookId);
        } else if (categoryId != null) {
            couponService.addCouponTargets(couponPolicyId, categoryId);
        }
        model.addAttribute("createdCouponPolicyId", couponPolicyId);

        return "redirect:/admin/coupon-register";
    }

    // 쿠폰생성
    @PostMapping("/admin/coupons")
    public String createCoupon(@ModelAttribute CouponCreateRequestDto createRequest) {
        couponService.createCoupon(createRequest);
        return "redirect:/admin/coupon-register";
    }

}
