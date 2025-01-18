package com.nhnacademy.bookstorefront.main.controller.coupon;

import com.nhnacademy.bookstorefront.main.dto.coupon.CouponPolicyResponseDto;
import com.nhnacademy.bookstorefront.main.service.CouponService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class AdminCouponPolicyFindController {
    private final CouponService couponService;

    // 쿠폰정책 조회
    @GetMapping("/admin/coupon-policies/active")
    public String getAllCouponPolicy(Model model, @RequestParam(defaultValue = "true") boolean couponActive,
                                     @RequestParam(defaultValue = "0") @Min(0) int page,
                                     @RequestParam(defaultValue = "10") @Min(1) int pageSize) {

        Page<CouponPolicyResponseDto> couponPolicies = couponService.findActiveCouponPolicies(couponActive, page, pageSize);

        model.addAttribute("couponPolicies", couponPolicies);
        model.addAttribute("couponActive", couponActive);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", couponPolicies != null ? couponPolicies.getTotalPages() : 0);

        return "admin/coupon/coupon-policy-find";
    }
}
