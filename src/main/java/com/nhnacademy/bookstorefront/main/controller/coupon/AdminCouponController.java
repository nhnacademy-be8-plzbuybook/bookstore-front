package com.nhnacademy.bookstorefront.main.controller.coupon;

import com.nhnacademy.bookstorefront.main.dto.coupon.CouponHistoryResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.MemberCouponCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.MemberCouponResponseDto;
import com.nhnacademy.bookstorefront.main.service.CouponService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminCouponController {
    private final CouponService couponService;

    // 쿠폰 발급 페이지 폼
    @GetMapping("/admin/coupon-issue")
    public String couponIssue() {
        return "admin/coupon/coupon-issue";
    }

    // 회원에게 쿠폰 발급
    @PostMapping("/admin/member-coupons")
    public String createMemberCoupon(@ModelAttribute MemberCouponCreateRequestDto memberCouponCreateRequestDto, Model model) {
        couponService.createMemberCoupon(memberCouponCreateRequestDto);

        return "admin/coupon/coupon-issue";
    }

    // 회원쿠폰 전체 조회
    @GetMapping("/admin/member-coupons")
    public String getAllMemberCoupons(@RequestParam(defaultValue = "0") @Min(0) int page,
                                      @RequestParam(defaultValue = "10") @Min(1) int pageSize,
                                      Model model) {
        Page<MemberCouponResponseDto> memberCoupons = couponService.getAllMemberCoupons(page, pageSize);

        model.addAttribute("memberCoupons", memberCoupons);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", memberCoupons != null ? memberCoupons.getTotalPages() : 0);

        return "admin/coupon/coupon-member-find";
    }

    // 쿠폰 ID에 해당하는 쿠폰 이력 목록 조회
    @GetMapping("/admin/coupon-histories/{coupon-id}")
    public String getCouponHistories(@PathVariable("coupon-id") Long couponId, @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int pageSize, Model model) {
        Page<CouponHistoryResponseDto> couponHistories = couponService.getHistoryByCouponId(couponId, page, pageSize);

        model.addAttribute("couponHistories", couponHistories);
        model.addAttribute("couponId", couponId);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", couponHistories != null ? couponHistories.getTotalPages() : 0);

        return "admin/coupon/coupon-histories";
    }


}
