package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.CouponClient;
import com.nhnacademy.bookstorefront.main.dto.coupon.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class AdminCouponController {
    private final CouponClient couponClient;

    // 쿠폰정책 등록
    @PostMapping("/admin/coupon-policies")
    public String createCouponPolicy(@ModelAttribute CouponPolicySaveRequestDto couponPolicySaveRequestDto, Model model) {
        couponClient.createCouponPolicy(couponPolicySaveRequestDto);
        return "redirect:/adminpage";
    }

    // 유효한 쿠폰정책 조회
    @GetMapping("/admin/coupon-policies")
    public String findActiveCouponPolicies(@RequestParam(defaultValue = "true") boolean couponActive,
                                           @RequestParam(defaultValue = "0") @Min(0) int page,
                                           @RequestParam(defaultValue = "10") @Min(1) int pageSize,
                                           Model model) {
        Page<CouponPolicyResponseDto> couponPolicies = couponClient.findActiveCouponPolicies(couponActive, page, pageSize).getBody();

        model.addAttribute("couponPolicies", couponPolicies);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", couponPolicies != null ? couponPolicies.getTotalPages() : 0);
        return "admin/adminPage";
    }

    // 쿠폰생성
    @PostMapping("/admin/coupons")
    public String createCoupon(@ModelAttribute CouponCreateRequestDto createRequest) {
        couponClient.createCoupon(createRequest);
        return "redirect:/adminpage";
    }

    // 전체 쿠폰 목록 조회
    @GetMapping("/admin/coupons")
    public String getAllCoupons(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) int pageSize, Model model) {
        Page<CouponResponseDto> coupons = couponClient.getAllCoupons(page, pageSize).getBody();

        model.addAttribute("coupons", coupons);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", coupons != null ? coupons.getTotalPages() : 0);


        return "admin/adminPage";
    }

    // 회원에게 쿠폰 발급
    @PostMapping("/admin/member-coupons")
    public ResponseEntity<String> createMemberCoupon(@ModelAttribute MemberCouponCreateRequestDto memberCouponCreateRequestDto, Model model) {
        try {
            couponClient.createMemberCoupon(memberCouponCreateRequestDto);
            model.addAttribute("successMessage", "쿠폰 발급이 성공적으로 완료되었습니다!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "쿠폰 발급에 실패하였습니다: " + e.getMessage());
        }

        return ResponseEntity.ok("쿠폰 발급이 성공적으로 완료되었습니다!");
    }


    // 쿠폰 ID로 회원에게 쿠폰 발급 조회

    // 특정 기간의 쿠폰 이력 조회


}
