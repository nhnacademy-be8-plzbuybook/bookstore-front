package com.nhnacademy.bookstorefront.main.controller.coupon;


import com.nhnacademy.bookstorefront.main.dto.coupon.CouponResponseDto;
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
public class AdminCouponFindController {
    private final CouponService couponService;

    // 쿠폰조회
    @GetMapping("/admin/coupons")
    public String getAllCoupons(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) int pageSize, Model model) {
        Page<CouponResponseDto> coupons = couponService.getAllCoupons(page, pageSize);

        model.addAttribute("coupons", coupons);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", coupons != null ? coupons.getTotalPages() : 0);

        return "admin/coupon/coupon-find";
    }

}
