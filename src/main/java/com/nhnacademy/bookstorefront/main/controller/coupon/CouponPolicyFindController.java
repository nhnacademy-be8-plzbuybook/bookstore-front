package com.nhnacademy.bookstorefront.main.controller.coupon;

import com.nhnacademy.bookstorefront.main.client.CouponClient;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponPolicyResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponPolicySaveRequestDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponTargetResponseDto;
import com.nhnacademy.bookstorefront.main.dto.coupon.CouponTargetSaveRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class CouponPolicyFindController {
    private final CouponClient couponClient;

    // 쿠폰정책 조회
    @GetMapping("/admin/coupon-policies")
    public String getAllCouponPolicy(Model model, @RequestParam(defaultValue = "true") boolean couponActive,
                                     @RequestParam(defaultValue = "0") @Min(0) int page,
                                     @RequestParam(defaultValue = "10") @Min(1) int pageSize) {

        Page<CouponPolicyResponseDto> couponPolicies = couponClient.findActiveCouponPolicies(couponActive, page, pageSize).getBody();

        model.addAttribute("couponPolicies", couponPolicies);
        model.addAttribute("couponActive", couponActive);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", couponPolicies!= null ? couponPolicies.getTotalPages() : 0 );

        return "admin/coupon/coupon-policy-find";
    }
}
